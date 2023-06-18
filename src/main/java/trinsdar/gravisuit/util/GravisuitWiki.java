package trinsdar.gravisuit.util;

import ic2.core.platform.events.impl.WikiEvent;
import ic2.core.wiki.base.IChapterBuilder;
import ic2.core.wiki.components.builders.CategoryObj;
import ic2.core.wiki.components.builders.HeaderObj;
import ic2.core.wiki.components.builders.IWikiObj;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GravisuitWiki {

    @SubscribeEvent
    public static void onWikiEvent(WikiEvent event){
        if (event instanceof WikiEvent.WikiSetupEvent setupEvent){
            setupEvent.builder.addChapterBuilder(GravisuitWiki::createWiki);
        }
        if (event instanceof WikiEvent.TableOfContentsSetupEvent setupEvent){
            IChapterBuilder builder = setupEvent.chapterBuilder;
            builder.addSimplePage(new HeaderObj("wiki.ic2.page.table.head"), new CategoryObj("wiki.ic2.category.general", new CategoryObj.Link(Registry.GRAVITOOL, "gravisuit:items").with("wiki.ic2.category.tutorials")));
        }
    }

    public static void createWiki(IChapterBuilder builder){
        builder.startBuildChapter("gravisuit", "items");
        builder.addSubPages(Registry.GRAVITOOL, Registry.RELOCATOR, Registry.VAJRA);
        builder.finishBuildChapter(true);
    }
}
