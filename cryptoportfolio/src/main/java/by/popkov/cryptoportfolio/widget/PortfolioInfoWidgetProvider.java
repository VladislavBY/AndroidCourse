package by.popkov.cryptoportfolio.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import by.popkov.cryptoportfolio.MainActivity;
import by.popkov.cryptoportfolio.R;
import by.popkov.cryptoportfolio.data_classes.PortfolioInfo;
import by.popkov.cryptoportfolio.data_classes.PortfolioInfoForView;
import by.popkov.cryptoportfolio.data_classes.PortfolioInfoForViewMapper;
import by.popkov.cryptoportfolio.data_classes.PortfolioInfoMapper;
import by.popkov.cryptoportfolio.domain.Coin;
import by.popkov.cryptoportfolio.domain.CoinMapper;
import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepository;
import by.popkov.cryptoportfolio.repositories.api_repository.ApiRepositoryImp;
import by.popkov.cryptoportfolio.repositories.database_repository.DatabaseRepository;
import by.popkov.cryptoportfolio.repositories.database_repository.DatabaseRepositoryImp;
import by.popkov.cryptoportfolio.repositories.settings_repository.SettingsRepository;
import by.popkov.cryptoportfolio.repositories.settings_repository.SettingsRepositoryImp;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

import static android.app.PendingIntent.*;

public class PortfolioInfoWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        if (context != null) {
            for (int appWidgetId : appWidgetIds) {
                setData(context, appWidgetManager, appWidgetId);
            }
        }
    }

    private void showThrowable(@NotNull Throwable throwable, @NonNull Context context) {
        Toast.makeText(context, throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    private void setData(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        ApiRepository apiRepository = new ApiRepositoryImp();
        DatabaseRepository databaseRepository = new DatabaseRepositoryImp(context, new CoinMapper());
        SettingsRepository settingsRepository = new SettingsRepositoryImp(context);
        Function<List<Coin>, PortfolioInfo> portfolioInfoMapper = new PortfolioInfoMapper();
        Function<PortfolioInfo, PortfolioInfoForView> portfolioInfoForViewMapper = new PortfolioInfoForViewMapper();
        try {
            List<Coin> rawCoinsList = databaseRepository.getCoinListFuture().get();
            apiRepository.getCoinsList(rawCoinsList, settingsRepository.getFiatSetting())
                    .map(coinList -> portfolioInfoForViewMapper.apply(portfolioInfoMapper.apply(coinList)))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            portfolioInfoForView -> setDataToView(portfolioInfoForView, context, appWidgetManager, appWidgetId),
                            throwable -> showThrowable(throwable, context)
                    );
        } catch (ExecutionException | InterruptedException e) {
            if (e.getCause() != null) {
                showThrowable(e.getCause(), context);
            }
        }
    }

    private void setDataToView(PortfolioInfoForView portfolioInfoForView, Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        if (portfolioInfoForView != null) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_portfolio_info);
            remoteViews.setTextViewText(R.id.sumTextView, portfolioInfoForView.getSum());
            remoteViews.setTextViewText(R.id.change24PrsTextView, portfolioInfoForView.getChangePercent24Hour());
            remoteViews.setTextColor(R.id.change24PrsTextView, portfolioInfoForView.getChange24Color());
            remoteViews.setTextViewText(R.id.change24TextView, portfolioInfoForView.getChange24Hour());
            remoteViews.setTextColor(R.id.change24TextView, portfolioInfoForView.getChange24Color());
            remoteViews.setOnClickPendingIntent(R.id.rootLayout,
                    getActivity(context, 0, new Intent(context, MainActivity.class), FLAG_UPDATE_CURRENT));
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }
}