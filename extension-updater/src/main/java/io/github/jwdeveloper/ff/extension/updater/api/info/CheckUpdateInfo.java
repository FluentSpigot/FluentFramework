package io.github.jwdeveloper.ff.extension.updater.api.info;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckUpdateInfo {

    private boolean isUpdate;
    private UpdateInfoResponse updateInfo;


}
