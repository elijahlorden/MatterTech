package lordlorden.mattertech.handlers;

import org.lwjgl.opengl.GL11;

import li.cil.oc.api.event.RackMountableRenderEvent;
import lordlorden.mattertech.item.ItemMolecularScanner;
import lordlorden.mattertech.item.ItemTargetingScanner;
import lordlorden.mattertech.oc.driver.DriverTargetingScanner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class RackRenderer {
	
	static final ResourceLocation
	//Molecular scanner textures
	mScannerActive = new ResourceLocation("mattertech","textures/blocks/mountablemolecularscanner_active.png"),
	mScanner10 = new ResourceLocation("mattertech","textures/blocks/mountablemolecularscanner_prog_1.png"),
	mScanner20 = new ResourceLocation("mattertech","textures/blocks/mountablemolecularscanner_prog_2.png"),
	mScanner30 = new ResourceLocation("mattertech","textures/blocks/mountablemolecularscanner_prog_3.png"),
	mScanner40 = new ResourceLocation("mattertech","textures/blocks/mountablemolecularscanner_prog_4.png"),
	mScanner50 = new ResourceLocation("mattertech","textures/blocks/mountablemolecularscanner_prog_5.png"),
	mScanner60 = new ResourceLocation("mattertech","textures/blocks/mountablemolecularscanner_prog_6.png"),
	mScanner70 = new ResourceLocation("mattertech","textures/blocks/mountablemolecularscanner_prog_7.png"),
	mScanner80 = new ResourceLocation("mattertech","textures/blocks/mountablemolecularscanner_prog_8.png"),
	mScanner90 = new ResourceLocation("mattertech","textures/blocks/mountablemolecularscanner_prog_9.png"),
	//Targeting scanner textures
	tScannerActive = new ResourceLocation("mattertech","textures/blocks/mountabletargetingscanner_active.png"),
	tScannerEstablishing = new ResourceLocation("mattertech","textures/blocks/mountabletargetingscanner_establishing.png"),
	tScannerStab = new ResourceLocation("mattertech","textures/blocks/mountabletargetingscanner_stabpulse.png"),
	tScanner1 = new ResourceLocation("mattertech","textures/blocks/mountabletargetingscanner_prog_1.png"),
	tScanner2 = new ResourceLocation("mattertech","textures/blocks/mountabletargetingscanner_prog_2.png"),
	tScanner3 = new ResourceLocation("mattertech","textures/blocks/mountabletargetingscanner_prog_3.png"),
	tScanner4 = new ResourceLocation("mattertech","textures/blocks/mountabletargetingscanner_prog_4.png"),
	tScanner5 = new ResourceLocation("mattertech","textures/blocks/mountabletargetingscanner_prog_5.png"),
	tScanner6 = new ResourceLocation("mattertech","textures/blocks/mountabletargetingscanner_prog_6.png"),
	tScanner7 = new ResourceLocation("mattertech","textures/blocks/mountabletargetingscanner_prog_7.png");
					
											
	static TextureAtlasSprite mScannerFront, tScannerFront;
	
	@SubscribeEvent
	public static void textureHook(TextureStitchEvent.Pre e) {
		System.out.println("[MatterTech] loading OC Rack Textures");
		mScannerFront = e.getMap().registerSprite(new ResourceLocation("mattertech:blocks/mountablemolecularscanner"));
		tScannerFront = e.getMap().registerSprite(new ResourceLocation("mattertech:blocks/mountabletargetingscanner"));
	}
	
	@SubscribeEvent
	public static void onRackMountableRender(RackMountableRenderEvent.Block e) {
		Item item = e.rack.getStackInSlot(e.mountable).getItem();
		if (item instanceof ItemMolecularScanner) {
			e.setFrontTextureOverride(mScannerFront);
		} else if (item instanceof ItemTargetingScanner) {
			e.setFrontTextureOverride(tScannerFront);
		}
	}
	
	@SubscribeEvent
	public static void onRackMountableRender(RackMountableRenderEvent.TileEntity e) {
		if (e.data == null) return;
		Item item = e.rack.getStackInSlot(e.mountable).getItem();
		if (item instanceof ItemMolecularScanner) {
			startRender();
			if (e.data.getBoolean("scanning")) e.renderOverlay(mScannerActive);
			int progress = e.data.getInteger("progress");
			if (progress > 90) {
				e.renderOverlay(mScanner90);
			} else if (progress >= 80) {
				e.renderOverlay(mScanner80);
			} else if (progress >= 70) {
				e.renderOverlay(mScanner70);
			} else if (progress >= 60) {
				e.renderOverlay(mScanner60);
			} else if (progress >= 50) {
				e.renderOverlay(mScanner50);
			} else if (progress >= 40) {
				e.renderOverlay(mScanner40);
			} else if (progress >= 30) {
				e.renderOverlay(mScanner30);
			} else if (progress >= 20) {
				e.renderOverlay(mScanner20);
			} else if (progress >= 10) {
				e.renderOverlay(mScanner10);
			}
			endRender();
		} else if (item instanceof ItemTargetingScanner) {
			startRender();
			int state = e.data.getInteger("lockState");
			if (state == DriverTargetingScanner.lockStateActive) e.renderOverlay(tScannerActive);
			else if (state == DriverTargetingScanner.lockStateEstablishing) e.renderOverlay(tScannerEstablishing);
			if (e.data.getInteger("lastStab") < 5) e.renderOverlay(tScannerStab);
			int strength = e.data.getInteger("lockStrength");
			if (strength >= 95) {
				e.renderOverlay(tScanner7);
			} else if (strength >= 70) {
				e.renderOverlay(tScanner6);
			} else if (strength >= 60) {
				e.renderOverlay(tScanner5);
			} else if (strength >= 50) {
				e.renderOverlay(tScanner4);
			} else if (strength >= 30) {
				e.renderOverlay(tScanner3);
			} else if (strength >= 20) {
				e.renderOverlay(tScanner2);
			} else if (strength >= 10) {
				e.renderOverlay(tScanner1);
			}
			endRender();
		}
	}
	
	private static void startRender() {
		Minecraft.getMinecraft().entityRenderer.disableLightmap();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	private static void endRender() {
		GlStateManager.disableBlend();
		Minecraft.getMinecraft().entityRenderer.enableLightmap();
		RenderHelper.enableStandardItemLighting();
	}
	
	
	
}
