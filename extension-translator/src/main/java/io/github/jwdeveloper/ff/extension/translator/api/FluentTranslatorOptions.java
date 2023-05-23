package io.github.jwdeveloper.ff.extension.translator.api;

import io.github.jwdeveloper.ff.plugin.api.extention.ExtensionOptions;
import lombok.Data;

@Data
public class FluentTranslatorOptions extends ExtensionOptions
{
      private String configPath = "plugin.languages";

      private String commandName = "lang";

      private String permissionName = "lang";

      // server/plugins/<plugin>/ + translationsPath
      private String translationsPath = "languages";
}
