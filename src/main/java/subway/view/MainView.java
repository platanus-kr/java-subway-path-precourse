package subway.view;

import java.util.Map;
import java.util.Scanner;
import subway.Subway;
import subway.model.MenuGroup.Menu;
import subway.util.Constants;
import subway.util.MessageUtils;

public class MainView extends AbstractView {

    private Map<String, Runnable> menuActionMap;

    public MainView(Subway subway, Scanner scanner) {
        super(subway, scanner);
    }

    @Override
    public void initView() {
        LineView lineView = new LineView(subway, this.scanner);
        StationView stationView = new StationView(subway, this.scanner);
        SectionView sectionView = new SectionView(subway, this.scanner);
        InquiryView inquiryView = new InquiryView(subway, this.scanner);

        menuActionMap = Map.of(
            "1", stationView::start,
            "2", lineView::start,
            "3", sectionView::start,
            "4", this::showWholeSubway,
            "5", inquiryView::start,
            Constants.EXIT_INPUT_CHARACTER, this::goBackward
        );
    }

    @Override
    public Menu getMenu() {
        return Constants.MENU_GROUPS.get(Constants.MAIN_MENU_STATE);
    }

    @Override
    public Map<String, Runnable> getMenuActionMap() {
        return menuActionMap;
    }

    public void showWholeSubway() {
        MessageUtils.printAnnouncement(Constants.TITLE_SUBWAY_MAP);
        subway.getSectionRepository().findAll() // Map<Line, List<Station>>
            .forEach((line, stations) -> {  // k:Line, v:List<Station>>
                MessageUtils.printInfoEntry(line.getName()); // lineName
                MessageUtils.printInfoEntry(Constants.SEPARATE_STRING_SUBWAY_MAP); // ---
                stations.forEach(station -> MessageUtils.printInfoEntry(station.getName()));// stN
                MessageUtils.printBlankLine();
            });
    }

}
