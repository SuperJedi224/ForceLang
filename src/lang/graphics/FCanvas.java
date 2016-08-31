package lang.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;

import lang.FBool;
import lang.FNum;
import lang.FObj;
import lang.ForceLang;
import lang.Function;
import lang.exceptions.IllegalInvocationException;

import java.io.File;

public class FCanvas extends FObj{
	class ImageShower extends JDialog{
		public ImageShower(){
			super(null,"Preview Image",ModalityType.APPLICATION_MODAL);
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.setContentPane(new JComponent(){
				{setMinimumSize(new Dimension(w,h));}
				public void paint(Graphics g){
					this.setSize(w, h);
					g.drawImage(img,0,0,w,h,null);
					ImageShower.this.pack();
				}
				public Dimension getPreferredSize(){
					return new Dimension(w,h);
				}
			});
			this.setSize(w,h);
			this.setVisible(true);
		}
	}
	BufferedImage img;
	int w=1,h=1;
	int direction=0;
	int x,y;
	int penSize;
	boolean penDown=true;
	int color=Color.decode("#000000").getRGB();
	private Graphics2D g;
	private FCanvas(){
		this.set("setPenColor",new Function(a->{
			color=Color.decode(String.format("#%6x",((FNum)ForceLang.parse(a)).intValue())).getRGB();
			return null;
		}));
		this.set("setPenSize",new Function(a->{
			penSize=((FNum)ForceLang.parse(a)).intValue();
			return null;
		}));
		this.set("show",new Function(a->{
			new ImageShower();
			return null;
		}));
		this.set("save",new Function(a->{
			a=ForceLang.parse(a).toString();
			try {
				ImageIO.write(img,a.endsWith("png")?"png":"jpg",new File(a));
			} catch (Exception e) {
				System.err.println("Saving image failed.");
			}
			return null;
		}));
		this.set("setPenDown",new Function(a->{
			penDown=FBool.valueOf(ForceLang.parse(a)).isTruthy();
			return null;
		}));
		this.set("move",new Function(a->{
			int x1=x;
			int y1=y;
			double dist=((FNum)ForceLang.parse(a)).doubleValue();
			x1+=dist*Math.cos(Math.toRadians(direction));
			y1+=dist*Math.sin(Math.toRadians(direction));
			if(penDown){
				g.setColor(new Color(color));
				g.setStroke(new BasicStroke(penSize));
				g.drawLine(x, y, x1, y1);
			}
			x=x1;
			y=y1;
			return null;
		}));
		this.set("getWidth",new Function(a->{
			if(a!=null)throw new IllegalInvocationException("getWidth is a nulladic function.");
			return new FNum(img.getWidth());
		}));
		this.set("getHeight",new Function(a->{
			if(a!=null)throw new IllegalInvocationException("getWidth is a nulladic function.");
			return new FNum(img.getHeight());
		}));
		this.set("turnPen",new Function(a->{
			direction+=((FNum)ForceLang.parse(a)).intValue();
			return null;
		}));
		this.setImmutable();
	}
	public FCanvas(FObj o){
		this();
		w=((FNum)o.get("width")).intValue();
		h=((FNum)o.get("height")).intValue();
		img=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
		FObj o2=o.get("bg");
			int i;
			if(o2!=null){i=Color.decode(String.format("#%6x",((FNum)o2).intValue())).getRGB();}else{i=Color.decode("#FFFFFF").getRGB();}
			for(int x=0;x<w;x++){
				for(int y=0;y<w;y++){
					img.setRGB(x, y, i);
				}
		}
		o2=o.get("pen");
		if(o2!=null){
			color=Color.decode(String.format("#%6x",((FNum)o2).intValue())).getRGB();	
		}
		g=(Graphics2D)img.getGraphics();
	}
	public FCanvas(BufferedImage b){
		this();
		img=b;
		w=b.getWidth();
		h=b.getHeight();
		g=(Graphics2D)img.getGraphics();
	}
}