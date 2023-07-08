package cn.allay.codegen;

import cn.allay.dependence.BiomeType;
import com.squareup.javapoet.*;
import lombok.SneakyThrows;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Allay Project 2023/6/3
 *
 * @author daoge_cmd
 */
public class VanillaBiomeIdEnumGen {
    public static void main(String[] args) {
        generate();
    }

    private static final Map<String, BiomeData> BIOME_DATA = new LinkedHashMap<>();

    private static class BiomeData {
        int id;
        String type;
    }

    static {
        try {
            Map<String, BiomeData> unsorted = CodeGen.GSON.fromJson(Files.newBufferedReader(Path.of("Data/unpacked/biome_id_and_type.json")), new HashMap<String, BiomeData>() {
            }.getClass().getGenericSuperclass());
            unsorted.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.comparingInt(o -> o.id)))
                    .forEachOrdered(entry -> BIOME_DATA.put(entry.getKey(), entry.getValue()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final ClassName STRING_CLASS = ClassName.get("java.lang", "String");
    private static final ClassName INTEGER_CLASS = ClassName.get("java.lang", "Integer");
    private static final ClassName GETTER_CLASS = ClassName.get("lombok", "Getter");
    private static final String PACKAGE_NAME = "cn.allay.api.data";
    private static final Path TARGET_PATH = Path.of("Allay-API/src/main/java/cn/allay/api/data/VanillaBiomeId.java");
    private static final String JAVA_DOC = """
            Automatically generated by {@code cn.allay.codegen.VanillaBiomeIdEnumGen} <br>
            Allay Project <p>
            @author daoge_cmd
            """;

    @SneakyThrows
    public static void generate() {
        TypeSpec.Builder codeBuilder = TypeSpec.enumBuilder("VanillaBiomeId")
                .addJavadoc(JAVA_DOC)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(GETTER_CLASS)
                .addField(FieldSpec
                        .builder(STRING_CLASS, "name", Modifier.PRIVATE, Modifier.FINAL)
                        .build())
                .addField(FieldSpec
                        .builder(INTEGER_CLASS, "id", Modifier.PRIVATE, Modifier.FINAL)
                        .build())
                .addField(FieldSpec
                        .builder(STRING_CLASS, "type", Modifier.PRIVATE, Modifier.FINAL)
                        .build())
                .addMethod(MethodSpec.constructorBuilder()
                        .addParameter(STRING_CLASS, "name")
                        .addParameter(INTEGER_CLASS, "id")
                        .addParameter(STRING_CLASS, "type")
                        .addStatement("this.name = name")
                        .addStatement("this.id = id")
                        .addStatement("this.type = type")
                        .build()
                );
        for (var entry : BIOME_DATA.entrySet()) {
            var name = entry.getKey();
            var id = entry.getValue().id;
            var type = entry.getValue().type;
            codeBuilder.addEnumConstant(name.toUpperCase(), TypeSpec.anonymousClassBuilder("$S, $L, $S", name, id, type).build());
        }
        codeBuilder.addSuperinterface(BiomeType.class);
        var builtCode = codeBuilder.build();
        var javaFile = JavaFile.builder(PACKAGE_NAME, builtCode).build();
        String result = javaFile.toString().replace("import cn.allay.dependence.BiomeType", "import cn.allay.api.world.biome.BiomeType");
        Files.writeString(TARGET_PATH, result);
    }
}
