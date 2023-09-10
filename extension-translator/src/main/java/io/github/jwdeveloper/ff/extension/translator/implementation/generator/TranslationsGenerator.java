package io.github.jwdeveloper.ff.extension.translator.implementation.generator;

import com.google.gson.JsonParser;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import lombok.Setter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class TranslationsGenerator
{
    private static ExecutorService executor;
    private static final String ERROR = "ERROR CODE";


    public static class Options
    {
        @Setter
        private  String inputPath;

        @Setter
        private  String fromLanguage = "en";

        @Setter
        private List<String> languageToTranslate = List.of("pl","fr","de","cs","ko","ru","es","tr","zh","it","pt");
    }


    public static void run(Options options, Consumer<String> callback) throws InterruptedException, IOException {
         run(options);
         callback.accept("x");
    }

    public static void run(Options options) throws InterruptedException, IOException {
        executor = Executors.newSingleThreadExecutor();
        FileUtility.ensurePath(options.inputPath);
        for (var lang : options.languageToTranslate) {
            var file = doLanguage(options.fromLanguage, lang, options.inputPath);
            file.save(Path.of(options.inputPath, lang + ".yml").toString());
        }

        var lastConfigPath = Path.of(options.inputPath,"temp", (options.fromLanguage + ".yml")).toString();
        var fromConfig = YamlConfiguration.loadConfiguration(new File(Path.of(options.inputPath, options.fromLanguage + ".yml").toString()));
        fromConfig.save(lastConfigPath);
    }


    private static YamlConfiguration doLanguage(String from, String to, String inputPath) throws InterruptedException, IOException {
        var fromConfig = YamlConfiguration.loadConfiguration(new File(Path.of(inputPath, from + ".yml").toString()));
        var toConfig = YamlConfiguration.loadConfiguration(new File(Path.of(inputPath, to + ".yml").toString()));
        FluentLogger.LOGGER.info("=============== Generating language "+to+" ============================");
        List<Future> tasks = new ArrayList<>();

        //checking updated translations
        var lastConfigPath = Path.of(inputPath,"temp", from + ".yml").toString();
        if(FileUtility.pathExists(lastConfigPath))
        {
            var lastTranslation = YamlConfiguration.loadConfiguration(new File(lastConfigPath));
            for (var key : fromConfig.getKeys(true)) {
                if (fromConfig.isConfigurationSection(key)) {
                    continue;
                }
                if (!lastTranslation.contains(key)) {
                    continue;
                }
                var currentValue = fromConfig.getString(key);
                var lastValue = lastTranslation.getString(key);
                if(lastValue.equals(currentValue))
                {
                    continue;
                }
                FluentLogger.LOGGER.info("Updated Translation",key,"Changed from ",lastValue," to ",currentValue);
                toConfig.set(key,null);
            }
        }


        //create new translation
        for (var key : fromConfig.getKeys(true)) {
            if (fromConfig.isConfigurationSection(key)) {
                continue;
            }
            if (toConfig.contains(key)) {
                continue;
            }
            var value = fromConfig.getString(key);
            tasks.add(getHTMLAsync(to, value, s ->
            {
                toConfig.set(key, s);
            }));
        }

        //clear not used translations
        for (var key : toConfig.getKeys(true)) {
            if (fromConfig.isConfigurationSection(key)) {
                continue;
            }
            if (fromConfig.contains(key)) {
                continue;
            }
            toConfig.set(key,null);
        }


        FluentLogger.LOGGER.info("waiting for requests");
        while (true) {
            Thread.sleep(10);
            var wokring = tasks.stream().filter(e -> e.isDone() == false).toList();
            if (wokring.size() != 0) {
                continue;
            }
            break;
        }
        FluentLogger.LOGGER.info("Done");



        return toConfig;
    }

    private static Future<String> getHTMLAsync(String toLang, String text, Consumer<String> onDone) {
        return executor.submit(() -> getHTML(toLang, text, onDone));
    }

    private static String getHTML(String toLang, String text, Consumer<String> onDone) throws Exception {
        text = text.replaceAll(" ", "+");
        //var link = "https://api.mymemory.translated.net/get?q=" + text + "&langpair=en|" + toLang;


        //  var link = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=en&tl=pl&dt=t&dt=bd&dj=1&q=hi my name is mark";
        var builder = new StringBuilder();
        builder.append("https://translate.googleapis.com/translate_a/single?client=gtx")
                .append("&sl=en")
                .append("&tl=" + toLang)
                .append("&dt=t")
                .append("&dt=bd")
                .append("&dj=1")
                .append("&q="+text);
        var link = builder.toString();

        StringBuilder result = new StringBuilder();
        URL url = new URL(link);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(1000 * 5);
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        } catch (Exception e) {
            FluentLogger.LOGGER.error("ERROR", e);
            onDone.accept(ERROR+": "+text);
        }
        var content =result.toString();
        var json = new JsonParser().parse(content).getAsJsonObject();
        try {
            var sentences = json.getAsJsonArray("sentences");
            var output = StringUtils.EMPTY;
            for(var element : sentences)
            {
                output += element.getAsJsonObject().get("trans").getAsString();
            }
            onDone.accept(output);
        } catch (Exception e) {
            onDone.accept(ERROR+": "+text);
        }
        return result.toString();
    }
}
