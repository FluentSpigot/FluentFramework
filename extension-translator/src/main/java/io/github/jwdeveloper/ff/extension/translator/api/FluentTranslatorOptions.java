package io.github.jwdeveloper.ff.extension.translator.api;

import io.github.jwdeveloper.ff.plugin.api.extention.ExtensionOptions;
import lombok.Data;

@Data
public class FluentTranslatorOptions extends ExtensionOptions
{
      private String configPath = "plugin.translator";

      private String commandName = "language";

      private String permissionName = "language";

      // server/plugins/<plugin>/ + translationsPath
      private String translationsPath = "languages";
}
