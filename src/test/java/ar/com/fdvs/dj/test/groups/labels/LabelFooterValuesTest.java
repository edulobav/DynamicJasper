/*
 * DynamicJasper: A library for creating reports dynamically by specifying
 * columns, groups, styles, etc. at runtime. It also saves a lot of development
 * time in many cases! (http://sourceforge.net/projects/dynamicjasper)
 *
 * Copyright (C) 2008  FDV Solutions (http://www.fdvsolutions.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 *
 * License as published by the Free Software Foundation; either
 *
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 *
 */

package ar.com.fdvs.dj.test.groups.labels;


import ar.com.fdvs.dj.domain.*;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.LabelPosition;
import ar.com.fdvs.dj.domain.entities.DJGroupVariable;
import ar.com.fdvs.dj.test.BaseDjReportTest;
import net.sf.jasperreports.view.JasperDesignViewer;
import net.sf.jasperreports.view.JasperViewer;

import java.util.Date;
import java.util.Map;

public class LabelFooterValuesTest extends BaseDjReportTest {

    public DynamicReport buildReport() throws Exception {

        Style labelStyle = new Style("labelStyle");
        labelStyle.setBorder(Border.PEN_1_POINT());

        FastReportBuilder drb = new FastReportBuilder();
        drb.addColumn("State", "state", String.class.getName(), 30)
                .addColumn("Branch", "branch", String.class.getName(), 30)
                .addColumn("Product Line", "productLine", String.class.getName(), 50)
                .addColumn("Item", "item", String.class.getName(), 50)
                .addColumn("Item Code", "id", Long.class.getName(), 30, true)
                .addColumn("Quantity", "quantity", Long.class.getName(), 60, true)
                .addColumn("Amount", "amount", Float.class.getName(), 70, true)
                .addGroups(2)
                .setTitle("November " + getYear() + " sales report")
                .setSubtitle("This report was generated at " + new Date())
                .setPrintBackgroundOnOddRows(true)
                .setUseFullPageWidth(true);

        drb.addGlobalFooterVariable(drb.getColumn(4), DJCalculation.COUNT, null, new DJValueFormatter() {

            public String getClassName() {
                return String.class.getName();
            }


            public Object evaluate(Object value, Map fields, Map variables, Map parameters) {
                return (value == null ? "0" : value.toString()) + " Clients";
            }
        });

        DJGroupLabel label = new DJGroupLabel(new StringExpression() {
            @Override
            public Object evaluate(Map fields, Map variables, Map parameters) {
                return "SUM for state " + fields.get("state");
            }
        },labelStyle, LabelPosition.BOTTOM);

        DJGroupVariable groupVariable = new DJGroupVariable(drb.getColumn(5), DJCalculation.SUM, labelStyle, null, label);
        drb.getGroup(0).addFooterVariable(groupVariable);


        DynamicReport dr = drb.build();

        return dr;
    }

    public static void main(String[] args) throws Exception {
        LabelFooterValuesTest test = new LabelFooterValuesTest();
        test.testReport();
        test.exportToJRXML();
        JasperViewer.viewReport(test.jp);    //finally display the report report
        JasperDesignViewer.viewReportDesign(test.jr);
    }

}
