package trinsdar.gravisuit.util;

import ic2.core.platform.events.impl.WikiEvent;
import ic2.core.wiki.base.IChapterBuilder;
import ic2.core.wiki.components.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class GravisuitWiki {

    @SubscribeEvent
    public static void onWikiEvent(WikiEvent event){
        if (event instanceof WikiEvent.WikiSetupEvent setupEvent){
            setupEvent.builder.addChapterBuilder(GravisuitWiki::createWiki);
        }
    }

    public static void createWiki(IChapterBuilder builder){
        builder.startBuildChapter("gravisuit", "tools");
        builder.addSimplePage(new HeaderObj("wiki.gravisuit.header.tools"), new TextObj("wiki.gravisuit.preview.gravisuit_tools.desc"));
        builder.addSubPages(Registry.GRAVITOOL);
        builder.addSimplePage(new HeaderObj(Registry.RELOCATOR.getDescriptionId()), new CraftObj(Registry.RELOCATOR), new PreviewObj(Registry.RELOCATOR), new TextObj("wiki.gravisuit.preview.relocator.desc"), new DoublePageEndObj());
        builder.addSubPages(Registry.VAJRA, Registry.MAGNET, Registry.VOIDER);
        builder.finishBuildChapter(true);
        builder.startBuildChapter("gravisuit", "armor");
        builder.addSimplePage(new HeaderObj("wiki.gravisuit.header.armor"), new TextObj("wiki.gravisuit.preview.gravisuit_armor.desc"));
        builder.addSimplePage(createItemList(Registry.ADVANCED_LAPPACK, Registry.ULTIMATE_LAPPACK));
        builder.addSimplePage(createItemList(Registry.ADVANCED_ELECTRIC_JETPACK, Registry.ADVANCED_NUCLEAR_JETPACK));
        builder.addSimplePage(createItemList(Registry.GRAVITATION_JETPACK, Registry.NUCLEAR_GRAVITATION_JETPACK));
        builder.finishBuildChapter(true);
    }

    private static List<IWikiObj> createItemList(ItemLike... items){
        List<IWikiObj> objs = new ArrayList<>();
        objs.add(new PreviewObj(items));
        objs.add(new HeaderObj(items[0].asItem().getDescriptionId()));
        objs.add(new CraftObj(items));
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(items[0].asItem());
        objs.add((new TextObj("wiki." + location.getNamespace() + ".preview." + location.getPath() + ".desc")).setCutOff());
        objs.add(new LinkObj(location.getNamespace(), "subpage." + location.getPath()));
        return objs;
    }
}
