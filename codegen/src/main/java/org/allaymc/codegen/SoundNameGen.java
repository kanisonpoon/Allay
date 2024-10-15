package org.allaymc.codegen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import lombok.SneakyThrows;

import javax.lang.model.element.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

/**
 * @author daoge_cmd
 */
public class SoundNameGen {

    private static final Gson GSON = new GsonBuilder().create();

    @SneakyThrows
    public static void main(String[] args) {
        var names = new HashSet<String>();
        names.addAll(getMusicNames());
        names.addAll(getSoundNames());

        TypeSpec.Builder codeBuilder = TypeSpec.interfaceBuilder(ClassNames.SOUND)
                .addJavadoc("Auto generated by {@code org.allaymc.codegen.SoundNameGen}")
                .addModifiers(Modifier.PUBLIC);

        for (var name : names) {
            codeBuilder.addField(
                    FieldSpec.builder(ClassNames.STRING, name.replace(".", "_").toUpperCase(), Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                            .initializer("$S", name)
                            .build()
            );
        }


        var javaFile = JavaFile.builder(ClassNames.SOUND.packageName(), codeBuilder.build())
                .indent(CodeGenConstants.INDENT)
                .skipJavaLangImports(true)
                .build();
        Files.writeString(Path.of("api/src/main/java/org/allaymc/api/world/Sound.java"), javaFile.toString());
    }

    @SneakyThrows
    private static Set<String> getMusicNames() {
        try(var reader = Files.newBufferedReader(Path.of("data/resources/unpacked/music_definitions.json"))) {
            var musicNames = new HashSet<String>();
            JsonParser.parseReader(reader).getAsJsonObject().asMap().values().forEach(v -> {
                musicNames.add(v.getAsJsonObject().get("event_name").getAsString());
            });
            return musicNames;
        }
    }

    @SneakyThrows
    private static Set<String> getSoundNames() {
        try(var reader = Files.newBufferedReader(Path.of("data/resources/unpacked/sound_definitions.json"))) {
            return JsonParser.parseReader(reader)
                    .getAsJsonObject()
                    .getAsJsonObject("sound_definitions")
                    .asMap()
                    .keySet();
        }
    }
}