package cn.allay.codegen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.javapoet.*;
import lombok.SneakyThrows;
import org.cloudburstmc.nbt.NBTInputStream;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.nbt.NbtType;

import javax.lang.model.element.Modifier;
import java.io.DataInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

import static cn.allay.codegen.CodeGen.BLOCK_PALETTE_FILE_PATH;
import static cn.allay.codegen.CodeGen.BLOCK_PALETTE_NBT;
import static cn.allay.codegen.Utils.convertToCamelCase;

/**
 * Author: daoge_cmd <br>
 * Date: 2023/4/8 <br>
 * Allay Project <br>
 */
public class VanillaBlockPropertyTypeGen {
    private static final Path FILE_OUTPUT_PATH_BASE = Path.of("Allay-API/src/main/java/cn/allay/block/property/vanilla");

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @SneakyThrows
    public static void generate() {
        var blockPropertyInfos = generateBlockPropertyInfos();
        exportBlockPropertyInfosToFile(blockPropertyInfos);
        for (BlockPropertyTypeInfo blockPropertyTypeInfo : blockPropertyInfos) {
            if (blockPropertyTypeInfo.type == BlockPropertyType.ENUM) {
                generateEnumClass(blockPropertyTypeInfo);
            }
        }
        //generate VanillaBlockProperties.java
        TypeSpec.Builder codeBuilder = TypeSpec.classBuilder("VanillaBlockPropertyTypes")
                .addJavadoc(
                        "Author: daoge_cmd <br>\n" +
                        "Automatically generated by {@link cn.allay.codegen.VanillaBlockPropertyTypeGen} <br>\n" +
                        "Allay Project <br>\n")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(
                        MethodSpec.constructorBuilder()
                                .addModifiers(Modifier.PRIVATE)
                                .build());
        var enumPropertyClass = ClassName.get("cn.allay.block.property.type", "EnumPropertyType");
        var booleanPropertyClass = ClassName.get("cn.allay.block.property.type", "BooleanPropertyType");
        var intPropertyClass = ClassName.get("cn.allay.block.property.type", "IntPropertyType");
        for (BlockPropertyTypeInfo blockPropertyTypeInfo : blockPropertyInfos) {
            switch (blockPropertyTypeInfo.type) {
                case ENUM -> {
                    var enumClass = ClassName.get("cn.allay.block.property.vanilla.enums", blockPropertyTypeInfo.getEnumClassName());
                    codeBuilder.addField(
                            FieldSpec
                                    .builder(ParameterizedTypeName.get(enumPropertyClass, enumClass), blockPropertyTypeInfo.name.toUpperCase(), Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                                    .initializer("$T.createType($S ,$T.class ,$T.values()[0])", enumPropertyClass, blockPropertyTypeInfo.name, enumClass, enumClass)
                                    .build()
                    );
                }
                case BOOLEAN -> {
                    codeBuilder.addField(
                            FieldSpec
                                    .builder(booleanPropertyClass, blockPropertyTypeInfo.name.toUpperCase(), Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                                    .initializer("$T.createType($S, $N)", booleanPropertyClass, blockPropertyTypeInfo.name, blockPropertyTypeInfo.validValues.get(0))
                                    .build()
                    );
                }
                case INTEGER -> {
                    int min = Integer.MAX_VALUE;
                    int max = Integer.MIN_VALUE;
                    for (var value : blockPropertyTypeInfo.validValues) {
                        var intValue = Integer.parseInt(value);
                        if (intValue < min) {
                            min = intValue;
                        }
                        if (intValue > max) {
                            max = intValue;
                        }
                    }
                    codeBuilder.addField(
                            FieldSpec
                                    .builder(intPropertyClass, blockPropertyTypeInfo.name.toUpperCase(), Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                                    .initializer("$T.createType($S, $L, $L, $L)", intPropertyClass, blockPropertyTypeInfo.name, min, max, blockPropertyTypeInfo.validValues.get(0))
                                    .build()
                    );
                }
            }
        }
        var propertyClass = ClassName.get("cn.allay.block.property.type", "BlockPropertyType");
        var listClass = ParameterizedTypeName.get(ClassName.get("java.util", "List"), ParameterizedTypeName.get(propertyClass, WildcardTypeName.subtypeOf(Object.class)));
        String paramStr = blockPropertyInfos.stream().map(info -> info.name.toUpperCase()).collect(Collectors.joining(", "));
        codeBuilder.addField(
                FieldSpec
                        .builder(listClass, "VALUES", Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                        .initializer("List.of($N)", paramStr)
                        .build()
        );
        codeBuilder.addMethod(
                MethodSpec
                        .methodBuilder("values")
                        .returns(listClass)
                        .addStatement("return VALUES")
                        .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
                        .build()
        );
        var javaFile = JavaFile.builder("cn.allay.block.property.vanilla", codeBuilder.build()).build();
        Files.writeString(FILE_OUTPUT_PATH_BASE.resolve("VanillaBlockPropertyTypes.java"), javaFile.toString());
    }

    @SneakyThrows
    protected static void generateEnumClass(BlockPropertyTypeInfo info) {
        var enumName = info.getEnumClassName();
        TypeSpec.Builder codeBuilder = TypeSpec.enumBuilder(enumName)
                .addJavadoc(
                        "Author: daoge_cmd <br>\n" +
                        "Automatically generated by {@link cn.allay.codegen.VanillaBlockPropertyTypeGen} <br>\n" +
                        "Allay Project <br>\n")
                .addModifiers(Modifier.PUBLIC);
        for (var value : info.validValues) {
            codeBuilder.addEnumConstant(value.toUpperCase());
        }
        var javaFile = JavaFile.builder("cn.allay.block.property.vanilla.enums", codeBuilder.build()).build();
        var path = FILE_OUTPUT_PATH_BASE.resolve("enums/" + enumName + ".java");
        if (Files.exists(path))
            Files.delete(path);
        Files.writeString(path, javaFile.toString());
    }

    @SneakyThrows
    protected static List<BlockPropertyTypeInfo> generateBlockPropertyInfos() {
        Map<String, List<String>> propertyInfos = new HashMap<>();
        BLOCK_PALETTE_NBT.forEach(block -> block.getCompound("states").forEach((name, value) -> {
            if (!propertyInfos.containsKey(name)) {
                var validValues = new ArrayList<String>();
                validValues.add(value.toString());
                propertyInfos.put(name, validValues);
            } else {
                var validValues = propertyInfos.get(name);
                if (!validValues.contains(value.toString()))
                    validValues.add(value.toString());
            }
        }));
        var boolValidValues = List.of("false", "true");
        return propertyInfos.entrySet().stream().map(entry -> {
            var propertyType = getPropertyType(entry.getValue());
            return new BlockPropertyTypeInfo(entry.getKey(), propertyType == BlockPropertyType.BOOLEAN ? boolValidValues : entry.getValue(), propertyType);
        }).toList();
    }

    @SneakyThrows
    protected static void exportBlockPropertyInfosToFile(List<BlockPropertyTypeInfo> infos) {
        Map<String, Map<String, Object>> converted = new HashMap<>();
        for (var info : infos) {
            converted.put(info.name, info.toMapStyle());
        }
        System.out.println("block_properties.json has exported to Allay-Server/src/test/resources!");
        Files.writeString(Path.of("Allay-Server/src/test/resources/block_properties.json"), GSON.toJson(converted));
    }

    protected static BlockPropertyType getPropertyType(List<String> values) {
        try {
            Integer.parseInt(values.get(0));
            if (values.size() == 2)
                return BlockPropertyType.BOOLEAN;
            else
                return BlockPropertyType.INTEGER;
        } catch (NumberFormatException ignore) {
        }
        return BlockPropertyType.ENUM;
    }

    protected enum BlockPropertyType {
        BOOLEAN,
        INTEGER,
        ENUM
    }

    protected record BlockPropertyTypeInfo(String name, List<String> validValues, BlockPropertyType type) {
        public String getEnumClassName() {
            return convertToCamelCase(name);
        }

        //"name" is not included
        public Map<String, Object> toMapStyle() {
            var map = new HashMap<String, Object>();
            map.put("validValues", validValues);
            map.put("valueType", type);
            return map;
        }
    }
}
