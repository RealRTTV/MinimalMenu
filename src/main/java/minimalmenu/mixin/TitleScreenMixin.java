package minimalmenu.mixin;

import minimalmenu.config.ConfigHandler;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends ScreenMixin {
    @Shadow private String splashText;
    @Shadow @Final @Mutable private static Identifier EDITION_TITLE_TEXTURE;
    @Shadow @Final @Mutable private static Text COPYRIGHT;
    
    @Inject(method = "init", at = @At("HEAD"))
    protected void removeCopyrightText(CallbackInfo info) {
        if (ConfigHandler.REMOVE_COPYRIGHT) {
            COPYRIGHT = Text.of(""); //Lol
        } else {
            COPYRIGHT = new LiteralText("Copyright Mojang AB. Do not distribute!");
        }
    }
    
    @Inject(method = "init", at = @At("TAIL"))
    protected void init(CallbackInfo info) {
        if (ConfigHandler.REMOVE_EDITION) {
            EDITION_TITLE_TEXTURE = new Identifier("minimalmenu", "textures/gui/title/edition_empty.png");
        } else {
            EDITION_TITLE_TEXTURE = new Identifier("textures/gui/title/edition.png");
        }

        if (ConfigHandler.REMOVE_SPLASH) {
            splashText = null;
        }
    }

    //Removes realms notifications from the screen.
    @Inject(method = "init", at = @At("HEAD"))
    protected void setRealmsNotificationsToFalse(CallbackInfo info) {
        if (ConfigHandler.REMOVE_REALMS) {
            assert this.client != null;
            this.client.options.realmsNotifications = false;
        }
    }
}
