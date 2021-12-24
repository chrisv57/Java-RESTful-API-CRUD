/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cst8218.verm0101.jsf;

import java.awt.Color;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.convert.ConverterException;


@FacesConverter(forClass=Color.class)
public class SpriteColorConverter implements Converter {
    private static final Pattern RGBPattern = 
            Pattern.compile(
                    "\\s*\\d{1,3}\\s*,\\s*\\d{1,3}\\s*,\\s*\\d{1,3}\\s*"
            );
    
    private static final int NUM_COLOR_COMPONENTS = 3;

    @Override
    public Object getAsObject(FacesContext context, 
            UIComponent component, 
            String value) {
        Matcher RGBFormatValidator = RGBPattern.matcher(value);
        int[] RGBValues = new int[NUM_COLOR_COMPONENTS];
        String[] RGBStringVals;
        int colorIndex = 0;
        int RGBValue = 0;
        Color pickedColor = null;
        
       
        if (RGBFormatValidator.matches()) {
            RGBStringVals = value.split(",");
            for (String RGBStringVal : RGBStringVals) {
                RGBValue = Integer.parseInt(RGBStringVal);
                if (RGBValue < 0 || RGBValue > 255) {
                    FacesMessage errorMessage = new FacesMessage(
                            "RGB value(s) out of bounds");
                    throw new ConverterException(errorMessage);
                }
                RGBValues[colorIndex++] = RGBValue;
            }
            
            pickedColor = new Color(RGBValues[0], RGBValues[1], RGBValues[2]);
        } else {
            FacesMessage errorMessage = 
                    new FacesMessage("Invalid RGB value (RRR, GGG, BBB)");
            throw new ConverterException(errorMessage);
        }
        
        return pickedColor;
    }
    

    @Override
    public String getAsString(FacesContext context,
            UIComponent component,
            Object value) {
        String colorStr = "RRR, GGG, BBB";
        
        if (null == value) {
            FacesMessage errorMessage = 
                    new FacesMessage("Object is null");
            throw new ConverterException(errorMessage);
        } else if (!(value instanceof Color)) {
            FacesMessage errorMessage = 
                    new FacesMessage("Object is not a Color");
            throw new ConverterException(errorMessage);
        } else {
            Color color = (Color) value;
            colorStr = String.format("%d,%d,%d", color.getRed(),
                                                 color.getGreen(),
                                                 color.getBlue());
        }
        
        return colorStr;
    }
}
