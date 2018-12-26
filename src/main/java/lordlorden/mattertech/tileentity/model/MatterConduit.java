package lordlorden.mattertech.tileentity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * MatterConduit - Lord_Lorden
 * Created using Tabula 7.0.0
 */
public class MatterConduit extends ModelBase {
    public ModelRenderer core;
    public ModelRenderer lower;
    public ModelRenderer upper;
    public ModelRenderer Bounds;
    public ModelRenderer xpos;
    public ModelRenderer xneg;

    public MatterConduit() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.xneg = new ModelRenderer(this, 0, 0);
        this.xneg.setRotationPoint(-5.5F, 0.0F, 0.0F);
        this.xneg.addBox(-2.5F, -3.0F, -3.0F, 5, 6, 6, 0.0F);
        this.Bounds = new ModelRenderer(this, 0, 0);
        this.Bounds.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Bounds.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16, 0.0F);
        this.lower = new ModelRenderer(this, 0, 0);
        this.lower.setRotationPoint(0.0F, 5.5F, 0.0F);
        this.lower.addBox(-3.0F, -2.5F, -3.0F, 6, 5, 6, 0.0F);
        this.core = new ModelRenderer(this, 0, 0);
        this.core.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.core.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, 0.0F);
        this.upper = new ModelRenderer(this, 0, 0);
        this.upper.setRotationPoint(0.0F, -5.5F, 0.0F);
        this.upper.addBox(-3.0F, -2.5F, -3.0F, 6, 5, 6, 0.0F);
        this.xpos = new ModelRenderer(this, 0, 0);
        this.xpos.setRotationPoint(5.5F, 0.0F, 0.0F);
        this.xpos.addBox(-2.5F, -3.0F, -3.0F, 5, 6, 6, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.xneg.render(f5);
        this.Bounds.render(f5);
        this.lower.render(f5);
        this.core.render(f5);
        this.upper.render(f5);
        this.xpos.render(f5);
    }
}
