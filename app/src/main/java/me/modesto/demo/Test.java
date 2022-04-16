package me.modesto.demo;

import me.modesto.utils.AppContext;
import me.modesto.utils.Logger;
import me.modesto.utils.Utils;

/**
 * Description.
 *
 * @author Created by Modesto in 2022/4/16
 */
public class Test {

    public void test() {
        Logger.LoggerConfig config = Logger.init();
        Utils.init(AppContext.current);
    }

}
