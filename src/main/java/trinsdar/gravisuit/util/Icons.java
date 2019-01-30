package trinsdar.gravisuit.util;

import ic2.core.platform.textures.Sprites;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static ic2.core.platform.textures.Ic2Icons.addSprite;
import static ic2.core.platform.textures.Ic2Icons.addTextureEntry;

public class Icons {
    @SideOnly(Side.CLIENT)
    public static void loadSprites()
    {
        addSprite(new Sprites.SpriteData("gravisuit_items", "gravisuit:textures/gravisuit_items.png", new Sprites.SpriteInfo(4, 5)));
        addTextureEntry(new Sprites.TextureEntry("gravisuit_items", 0, 0, 4, 5));
    }
}
