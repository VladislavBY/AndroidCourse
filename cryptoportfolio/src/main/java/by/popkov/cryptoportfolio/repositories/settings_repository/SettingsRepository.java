package by.popkov.cryptoportfolio.repositories.settings_repository;

public interface SettingsRepository {
    void saveFiatSetting(String fiatSymbol);
    String getFiatSetting();
    void saveSortSetting(String sortType);
    String getSortSetting();
}
