package me.emafire003.dev.particleanimationlib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static me.emafire003.dev.particleanimationlib.ParticleAnimationLib.MOD_ID;

public class EffectSerializer {

    public static final String EFFECTS_FOLDER = "/stored_effects/";

    public static void serializeEffect(Effect effect, String filename) {
        Gson serializedEffect = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation()
                .create();

        try {
            Files.createDirectories(FabricLoader.getInstance().getConfigDir().resolve(MOD_ID+EFFECTS_FOLDER));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (FileWriter fileWriter = new FileWriter(String.valueOf(FabricLoader.getInstance().getConfigDir().resolve(MOD_ID+EFFECTS_FOLDER+filename+".json")), StandardCharsets.UTF_8);

             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter, 4096)) {

            serializedEffect.toJson(effect, bufferedWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /*try{
            Files.createDirectories(FabricLoader.getInstance().getConfigDir().resolve(MOD_ID+EFFECTS_FOLDER));
            //serializedEffect.toJson(effect, new FileWriter(String.valueOf(FabricLoader.getInstance().getConfigDir().resolve(MOD_ID+EFFECTS_FOLDER+filename+".json"))));
            //serializedEffect.toJson(effect);


            ParticleAnimationLib.LOGGER.info("The serialized thing is: " + serializedEffect);
        }catch (Exception e){
            e.printStackTrace();
        }*/



    }
}
