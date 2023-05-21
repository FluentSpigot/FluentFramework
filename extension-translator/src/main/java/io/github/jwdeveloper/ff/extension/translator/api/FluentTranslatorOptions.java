package io.github.jwdeveloper.ff.extension.translator.api;

import lombok.Data;

@Data
public class FluentTranslatorOptions
{
      private String configPath = "plugin.language";

      private String commandName = "lang";

      private String permissionName = "lang";

      // server/plugins/<plugin>/ + translationsPath
      private String translationsPath = "translations";
}
