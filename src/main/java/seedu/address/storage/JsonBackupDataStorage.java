package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.BackupData;
import seedu.address.model.ReadOnlyBackupData;

import java.nio.file.Path;

public class JsonBackupDataStorage implements BackupDataStorage {

    private Path filePath;

    public JsonBackupDataStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getBackupDataFilePath() {
        return filePath;
    }

    @Override
    public Optional<BackupData> readBackupData() throws DataConversionException {
        return readBackupData(filePath);
    }


    public Optional<BackupData> readBackupData(Path backupDataFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(backupDataFilePath, BackupData.class);
    }

    @Override
    public void saveBackupData(ReadOnlyBackupData backupData) throws IOException {
        JsonUtil.saveJsonFile(backupData, filePath);
    }
}