package dev.hevav.tchubbot.api;

import dev.hevav.tchubbot.types.Module;
import dev.hevav.tchubbot.modules.Admin;
import dev.hevav.tchubbot.modules.Help;
import dev.hevav.tchubbot.modules.Music;
import dev.hevav.tchubbot.modules.Status;
import net.dv8tion.jda.api.JDA;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.ref.WeakReference;

/**
 * Configuration for TchuBBak
 *
 * @author hevav
 * @since 1.0
 */
public class Config {
    //Modules to load globally
    public Module[] modules = new Module[]{
            new Admin(),
            new Music(),
            new Help(),
            new Status()
    };

    //YouTube v3 api token
    public String yt_token;
    //Discord bot token
    public String bot_token;
    //Prefix to trigger
    public String bot_prefix;
    public String log_level;
    public WeakReference<JDA> api_ref;

    private final Logger logger = LogManager.getLogger("TchuBBak");

    public Config(String[] args){
        yt_token = System.getenv("pf_yt_token");
        bot_token = System.getenv("pf_bot_token");
        bot_prefix = System.getenv("pf_bot_prefix");
        log_level = System.getenv("pf_log_level");
        for(String arg : args){
            String[] arg_split = arg.split("=");
            switch(arg_split[0]){
                case "bot_token":
                    bot_token = arg_split[1];
                    break;
                case "bot_prefix":
                    bot_prefix = arg_split[1];
                    break;
                case "log_level":
                    log_level = arg_split[1];
                    break;
                case "yt_token":
                    yt_token = arg_split[1];
                    break;
                default:
                    logger.warn(String.format("Wrong variable %s", arg_split[0]));
                    break;
            }
        }
    }

    public void main(){

    }
}