package dev.hevav.pfbot.modules;

import dev.hevav.pfbot.Boot;
import dev.hevav.pfbot.api.EmbedHelper;
import dev.hevav.pfbot.api.LocalizedString;
import dev.hevav.pfbot.api.Module;
import dev.hevav.pfbot.api.Trigger;
import net.dv8tion.jda.api.Region;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.ref.WeakReference;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
public class Status implements Module {

    private Boot boot;
    private LocalizedString shortLogDescription = new LocalizedString(
            "Сокращенные логи",
            "Short logs",
            null,
            null,
            null,
            null);
    private LocalizedString logDescription = new LocalizedString(
            "Статус",
            "Status",
            null,
            null,
            null,
            null);
    private LocalizedString upTimeString = new LocalizedString(
            "Аптайм",
            "Uptime",
            null,
            null,
            null,
            null);
    private LocalizedString pingString = new LocalizedString(
            "Пинг",
            "Ping",
            null,
            null,
            null,
            null);
    private LocalizedString guildsString = new LocalizedString(
            "Сервера",
            "Guilds",
            null,
            null,
            null,
            null);

    private final Logger logger = LogManager.getLogger("PFbot");

    public void onInit(WeakReference<Boot> _boot) {
        boot = _boot.get();
        logger.debug("Module Status was initialized");
    }
    @Override
    public Trigger[] triggers() {
        return new Trigger[]{new Trigger("sl", shortLogDescription), new Trigger("status", logDescription)};
    }

    @Override
    public void onMessage(GuildMessageReceivedEvent event, String trigger) {
        Region region = event.getGuild().getRegion();
        RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
        long uptime = rb.getUptime();
        String stringUptime = LocalTime.MIN.plus(
                Duration.ofMillis(uptime)
        ).toString();
        switch (trigger){
            case "sl":
                EmbedHelper.sendEmbed(shortLogDescription.getLocalizedString(region), "", event.getChannel(), Arrays.asList(
                        new MessageEmbed.Field(upTimeString.getLocalizedString(region), stringUptime, true)
                ));
                break;
            case "status":
                EmbedHelper.sendEmbed(logDescription.getLocalizedString(region), "", event.getChannel(), Arrays.asList(
                        new MessageEmbed.Field(upTimeString.getLocalizedString(region), stringUptime, true),
                        new MessageEmbed.Field(pingString.getLocalizedString(region), String.valueOf(boot.api.getGatewayPing()), true),
                        new MessageEmbed.Field(guildsString.getLocalizedString(region), String.valueOf(boot.api.getGuilds().size()), true)
                        ));
                break;
        }
    }
}