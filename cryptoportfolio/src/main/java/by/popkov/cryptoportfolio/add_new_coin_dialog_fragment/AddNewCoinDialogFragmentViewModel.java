package by.popkov.cryptoportfolio.add_new_coin_dialog_fragment;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import by.popkov.cryptoportfolio.domain.Coin;
import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepository;
import by.popkov.cryptoportfolio.repositories.database_repository.DatabaseRepository;
import by.popkov.cryptoportfolio.repositories.settings_repository.SettingsRepository;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

class AddNewCoinDialogFragmentViewModel extends AndroidViewModel {
    private ApiRepository apiRepository;
    private DatabaseRepository databaseRepository;
    private SettingsRepository settingsRepository;

    AddNewCoinDialogFragmentViewModel(
            Application application,
            ApiRepository apiRepository,
            DatabaseRepository databaseRepository,
            SettingsRepository settingsRepository
    ) {
        super(application);
        this.apiRepository = apiRepository;
        this.databaseRepository = databaseRepository;
        this.settingsRepository = settingsRepository;
    }

    void saveCoin(@NotNull String symbol, String number) {
        if (number == null || number.isEmpty()) {
            number = "0.0";
        }
        Coin coinToCheck = new Coin.Builder(UUID.randomUUID().toString() ,symbol.toUpperCase().trim(), Double.valueOf(number)).build();
        apiRepository.getCoin(coinToCheck, settingsRepository.getFiatSetting())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(coin -> databaseRepository.addNewCoin(coinToCheck), this::showThrowable);
    }

    private void showThrowable(@NotNull Throwable throwable) {
        Toast.makeText(getApplication(), throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }
}
