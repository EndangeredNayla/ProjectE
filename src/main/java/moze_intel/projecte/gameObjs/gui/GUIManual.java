package moze_intel.projecte.gameObjs.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import moze_intel.projecte.PECore;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GUIManual extends GuiScreen {
	
	private ResourceLocation bookTexture = new ResourceLocation("projecte:textures/gui/bookTexture.png");
	private ResourceLocation tocTexture = new ResourceLocation("projecte:textures/gui/bookTexture.png");
	private static ResourceLocation bookGui = new ResourceLocation("textures/gui/book.png");
	
	private int currentPage = -1;
	
		public GUIManual(){
			super();
		}
		
		@Override
		public void initGui(){
			
			int i = (this.width - 256) / 2;
			byte b0 = 2;
			
			PageTurnButton nextButton = new PageTurnButton(1, i + 210, b0 + 158, true);
			PageTurnButton prevButton = new PageTurnButton(2, i + 16, b0 + 158, false);
			TocButton tocButton = new TocButton(3, (this.width/2)-(TocButton.bWidth/2), b0+190);

	        this.buttonList.add(nextButton);
	        this.buttonList.add(prevButton);
	        this.buttonList.add(tocButton);
			
		}
		
		@Override
		public void drawScreen(int par1, int par2, float par3){		
			int yPos = 50;
			int xPos = 100;

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			
			if(this.currentPage == -1){
		    	this.mc.getTextureManager().bindTexture(tocTexture);
			}else{
		    	this.mc.getTextureManager().bindTexture(bookTexture);
			}
			
		    int k = (this.width - 256) / 2;
		    this.drawTexturedModalRect(k, 5, 0, 0, 256, 180);
		    
		    if(this.currentPage > -1){
		    	this.fontRendererObj.drawString(PECore.instance.manualPages.get(currentPage).getItemName(), k + 39, 27, 0, false);	
		    	this.fontRendererObj.drawSplitString(PECore.instance.manualPages.get(currentPage).getHelpInfo(), k + 18, 45, 225, 0);
		    }
		    
		    for(int i = 0; i < this.buttonList.size(); i++){
	            ((GuiButton) this.buttonList.get(i)).drawButton(this.mc, par1, par2);
	        }
		    
		    if(this.currentPage > -1){
		    	drawItemStackToGui(mc, PECore.instance.manualPages.get(currentPage).getItem(), k + 19, 22, !(PECore.instance.manualPages.get(currentPage).getItem() instanceof ItemBlock));
		    }
		    
		    this.updateButtons();
		}
		
		@Override
		public void onGuiClosed(){
			super.onGuiClosed();
		}
		
	    @Override
		protected void actionPerformed(GuiButton par1GuiButton){
	    	if(par1GuiButton.id == 1){
	    		this.currentPage++;
	    	}else if(par1GuiButton.id == 2){
	    		this.currentPage--;
	    	}else if(par1GuiButton.id == 3){
	    		this.currentPage = -1;
	    	}
	    	
	    	this.updateButtons();
	    }
		
	    private void updateButtons(){
	    	if(this.currentPage == -1){
	    		((PageTurnButton) this.buttonList.get(0)).visible = true;
	    		((PageTurnButton) this.buttonList.get(1)).visible = false;
	    		((TocButton) this.buttonList.get(2)).visible = false;
	    	}else if(this.currentPage == PECore.instance.manualPages.size() - 1){
	    		((PageTurnButton) this.buttonList.get(0)).visible = false;
	    		((PageTurnButton) this.buttonList.get(1)).visible = true;
	    		((TocButton) this.buttonList.get(2)).visible = true;
	    	}else{
	    		((PageTurnButton) this.buttonList.get(0)).visible = true;
	    		((PageTurnButton) this.buttonList.get(1)).visible = true;
	    		((TocButton) this.buttonList.get(2)).visible = true;
	    	}
	    }
	
	    @SideOnly(Side.CLIENT)
		static class TocButton extends GuiButton{
			public static int bWidth = 30;
			public static int bHeight = 15;
			
			public TocButton(int ID, int xPos, int yPos){
				super(ID,xPos,yPos, bWidth,bHeight, "ToC");
			}
	    }
	    
		@SideOnly(Side.CLIENT)
		static class PageTurnButton extends GuiButton {
			public static int bWidth = 23;
			public static int bHeight = 13;
			private boolean bool;
			
			public PageTurnButton(int ID, int xPos, int yPos, boolean par4){
				super(ID, xPos, yPos, bWidth, bHeight, "");
				bool = par4;
			}
			
			
			@Override
			public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_){
				if(this.visible){
					boolean flag = p_146112_2_ >= this.xPosition && p_146112_3_ >= this.yPosition && p_146112_2_ < this.xPosition + this.width && p_146112_3_ < this.yPosition + this.height;
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					p_146112_1_.getTextureManager().bindTexture(bookGui);
					int u = 0;
					int v = 192;

					if(flag){
						u += bWidth;
					}

					if(!this.bool){
						v += bHeight;
					}
					
					this.drawTexturedModalRect(this.xPosition, this.yPosition, u, v, bWidth, bHeight);
				}
			}
			
		}
		
		public static void drawItemStackToGui(Minecraft mc, Item item, int x, int y, boolean fixLighting){
			if(fixLighting){
				GL11.glEnable(GL11.GL_LIGHTING);
			}
			
	        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	        itemRender.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), new ItemStack(item), x, y);
	        
	        if(fixLighting){
	        	GL11.glDisable(GL11.GL_LIGHTING);
	        }
	        
	        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		}
		
		public static void drawItemStackToGui(Minecraft mc, Block block, int x, int y, boolean fixLighting){
			drawItemStackToGui(mc, Item.getItemFromBlock(block), x, y, fixLighting);
		}
		
		@Override
		public boolean doesGuiPauseGame(){	
			return false;
		}

		
}
