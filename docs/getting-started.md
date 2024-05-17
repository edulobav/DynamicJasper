---
layout: page
title: Getting Started!
# permalink: /about/
---

# Try the examples first

The source code comes with examples ready to run (you need Maven 2 to build the source code). below a listing of some TestCases that are a good starting point.

**[ar.com.fdvs.dj.test.FastReportTest](https://github.com/intive-FDV/DynamicJasper/tree/master/src/test/java/ar/com/fdvs/dj/test/FastReportTest.java)**  
A simple example that shows the basic setup of a report and its columns. [Download pdf](./sample-reports/FastReportTest.pdf)

**[ar.com.fdvs.dj.test.PlainReportTest](https://github.com/intive-FDV/DynamicJasper/tree/master/src/test/java/ar/com/fdvs/dj/test/PlainReportTest.java)**  
A example that shows how to get more control on the basic setup of a report and its columns. [Download pdf](./sample-reports/PlainReportTest.pdf)


**[ar.com.fdvs.dj.test.StylesReportTest](https://github.com/intive-FDV/DynamicJasper/tree/master/src/test/java/ar/com/fdvs/dj/test/TemplateStyleReportTest.java)**  
Based on the PlainReportTest test, shows how to add some styles to the different elements in the report. [Download pdf](./sample-reports/StylesReportTest.pdf)

**[ar.com.fdvs.dj.test.GroupsReportTest](https://github.com/intive-FDV/DynamicJasper/tree/master/src/test/java/ar/com/fdvs/dj/test/GroupsReportTest.java)**  
Based on the StylesReportTest test, this test shows how to define groups and variables at the header and footer of each group. [Download pdf](./sample-reports/GroupsReportTest.pdf)

**[ar.com.fdvs.dj.test.ConditionalStylesReportTest](https://github.com/intive-FDV/DynamicJasper/tree/master/src/test/java/ar/com/fdvs/dj/test/ConditionalStylesReportTest.java)**  
This test shows how to create a simple conditional style. The style of the values from a column changes as a certain condition is met. [Download pdf](./sample-reports/ConditionalStylesReportTest.pdf)

**[ar.com.fdvs.dj.test.ImageBannerReportTest](https://github.com/intive-FDV/DynamicJasper/tree/master/src/test/java/ar/com/fdvs/dj/test/ImageBannerReportTest.java)**  
This test shows how to add images to the title of the report and the header. [Download pdf](./sample-reports/ImageBannerReportTest.pdf)

**[ar.com.fdvs.dj.test.ChartReportTest](https://github.com/intive-FDV/DynamicJasper/tree/master/src/test/java/ar/com/fdvs/dj/test/ChartReportTest.java)**  
Based on GroupsReportTest, shows how to add charts to a report, using a chart builder. [Download pdf](./sample-reports/ChartReportTest.pdf)

**[ar.com.fdvs.dj.test.AutotextReportTest](https://github.com/intive-FDV/DynamicJasper/tree/master/src/test/java/ar/com/fdvs/dj/test/AutotextReportTest.java)**  
Based on FastReportTest, shows how to add autotext out-of-the-box such as “page x/y”, etc. [Download pdf](./sample-reports/AutotextReportTest.pdf)

**[ar.com.fdvs.dj.test.ReflectiveReportTest](https://github.com/intive-FDV/DynamicJasper/tree/master/src/test/java/ar/com/fdvs/dj/test/ReflectiveReportTest.java)**  
With almoust no code, you get an instant report! it uses the ReflectiveReportBuilder wich make some guesses upon the data type of the elements contained in the datasource and apply a style to them. [Download pdf](./sample-reports/ReflectiveReportTest.pdf)

[Here](https://github.com/intive-FDV/DynamicJasper/tree/master/src/test/java/ar/com/fdvs/dj/test) is the source code of the tests.

> **Note**: These tests opens a window that shows the report output (in a component called JasperViewer), besides that, tests classes extends `junit.framework.TestCase`. If you run the tests using JUnit runner, the JasperViewer will be closed after the tests finished. If you want to keep the JasperViewer open, run the tests through the `main(...)` method provided

Creating a simple Report
------------------------

This fragment shows how to create a report in an easy and fast way. [see the code](https://github.com/intive-FDV/DynamicJasper/tree/master/src/test/java/ar/com/fdvs/dj/test/FastReportTest.java)

```java
FastReportBuilder drb = new FastReportBuilder();
DynamicReport dr = drb.addColumn("State", "state", String.class.getName(),30)
.addColumn("Branch", "branch", String.class.getName(),30)
.addColumn("Product Line", "productLine", String.class.getName(),50)
.addColumn("Item", "item", String.class.getName(),50)
.addColumn("Item Code", "id", Long.class.getName(),30,true)
.addColumn("Quantity", "quantity", Long.class.getName(),60,true)
.addColumn("Amount", "amount", Float.class.getName(),70,true)
.addGroups(2)
.setTitle("November 2006 sales report")
.setSubtitle("This report was generated at " + new Date())
.setPrintBackgroundOnOddRows(true)
.setUseFullPageWidth(true)
.build();

JRDataSource ds = new JRBeanCollectionDataSource(TestRepositoryProducts.getDummyCollection());
JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);
JasperViewer.viewReport(jp);    //finally display the report report
```

This code produces the report shown below The **FastReportBuilder** provides the methods to pass the basic parameters to the report:

-   `addColumn( <title>, <field/property to show>, <class name of the property to show>, <width of the column> )`
-   `addGroups( <number of columns to group by>)`
- `setTitle(...)` , `setSubtitle(...)`: guess!!!
- `setUseFullPageWidth( <boolean> )`: if true, the columns width will be resized proportionally until meet the page width

In the previous report, the DynamicJasper API makes a lot of decisions setting default values to the styles in general (title, column header, report detail, etc). This defaults are defined in the `FastReportBuilder` class. More control in all this aspects can be achieved using the `DynamicReportBuilder` class, as shown in the next example.

## Getting more control on the report options

This code shows how to create a simple report but taking more control on the report options, the code is self explained. [see the code](https://github.com/intive-FDV/DynamicJasper/tree/master/src/test/java/ar/com/fdvs/dj/test/TemplateStyleReportTest.java)

```java
DynamicReportBuilder drb = new DynamicReportBuilder();

drb.setTitle("November 2006 sales report")		//defines the title of the report
.setSubtitle("The items in this report correspond "
+"to the main products: DVDs, Books, Foods and Magazines")
.setDetailHeight(15)		//defines the height for each record of the report
.setMargins(30, 20, 30, 15)		//define the margin space for each side (top, bottom, left and right)
.setDefaultStyles(titleStyle, subtitleStyle, headerStyle, detailStyle)
.setColumnsPerPage(1);		//defines columns per page (like in the telephone guide)

/**
* Note that we still didn't call the build() method
**/

/**
* Column definitions. We use a new ColumnBuilder instance for each
* column, the ColumnBuilder.getNew() method returns a new instance
* of the builder
*/
AbstractColumn columnState = ColumnBuilder.getNew()		//creates a new instance of a ColumnBuilder
.setColumnProperty("state", String.class.getName())		//defines the field of the data source that this column will show, also its type
.setTitle("State")		//the title for the column
.setWidth(85)		//the width of the column
.build();		//builds and return a new AbstractColumn

//Create more columns
AbstractColumn columnBranch = ColumnBuilder.getNew()
.setColumnProperty("branch", String.class.getName())
.setTitle("Branch").setWidth(85)
.build();

AbstractColumn columnaProductLine = ColumnBuilder.getNew()
.setColumnProperty("productLine", String.class.getName())
.setTitle("Product Line").setWidth(85)
.build();

AbstractColumn columnaItem = ColumnBuilder.getNew()
.setColumnProperty("item", String.class.getName())
.setTitle("Item").setWidth(85)
.build();

AbstractColumn columnCode = ColumnBuilder.getNew()
.setColumnProperty("id", Long.class.getName())
.setTitle("ID").setWidth(40)
.build();

AbstractColumn columnaCantidad = ColumnBuilder.getNew()
.setColumnProperty("quantity", Long.class.getName())
.setTitle("Quantity").setWidth(80)
.build();

AbstractColumn columnAmount = ColumnBuilder.getNew()
.setColumnProperty("amount", Float.class.getName())
.setTitle("Amount").setWidth(90)
.setPattern("$ 0.00")		//defines a pattern to apply to the values swhown (uses TextFormat)
.setStyle(amountStyle)		//special style for this column (align right)
.build();

/**
* We add the columns to the report (through the builder) in the order
* we want them to appear
*/
drb.addColumn(columnState);
drb.addColumn(columnBranch);
drb.addColumn(columnaProductLine);
drb.addColumn(columnaItem);
drb.addColumn(columnCode);
drb.addColumn(columnaCantidad);
drb.addColumn(columnAmount);

/**
* add some more options to the report (through the builder)
*/
drb.setUseFullPageWidth(true);  //we tell the report to use the full width of the page. this rezises
//the columns width proportionally to meat the page width.

DynamicReport dr = drb.build(); //Finally build the report!

JRDataSource ds = new JRBeanCollectionDataSource(TestRepositoryProducts.getDummyCollection());    //Create a JRDataSource, the Collection used
//here contains dummy hardcoded objects...

JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);    //Creates the JasperPrint object, we pass as a Parameter
//the DynamicReport, a new ClassicLayoutManager instance (this
//one does the magic) and the JRDataSource
JasperViewer.viewReport(jp); //finally display the report report
```

The **DynamicReportBuilder** builds **DynamicReport** objects, this object contains the main options of the report (title, subtitle, styles, page size, orientation and a list of columns and groups) The **ColumnBuilder** builds **SimpleColumn** and **ExpressionColumn** objects depending the case (they both inherit from **AbstractColumn**). Each column holds basic data like the column title, the field from the datasource to show (it uses BeanUtils, so “a.b.c” can be passed as a property parameter), and how to show this data (style, pattern to apply, etc).

> **Note** that the `property_name` passed to `ColumnBuilder.setColumnProperty("property_name", "property_class_name")` method must match the name of a field/property in the elements contained in the datasource

## Adding groups

Using the same code as before, we add this few lines. [see the code](https://github.com/intive-FDV/DynamicJasper/tree/master/src/test/java/ar/com/fdvs/dj/test/GroupsReportTest.java)

```java
GroupBuilder gb1 = new GroupBuilder();
ColumnsGroup g1 = gb1.addCriteriaColumn((PropertyColumn) columnState).build();
drb.addGroup(g1);              //add the group to the DynamicReportBuilder
```

Up to here, the DJ API will handle how to do the grouping stuff, but this isn’t enough for most of the users so lets see how to put some variables (totals) in the footer and header of the group.

```java
GroupBuilder gb1 = new GroupBuilder();
ColumnsGroup g1 = gb1.addCriteriaColumn((PropertyColumn) columnState)        //define the criteria column to group by (columnState)
.addFooterVariable(columnAmount,ColumnsGroupVariableOperation.SUM)        //tell the group place a variable in the footer
//of the column "columnAmount" with the SUM of all
//values of the columnAmount in this group.

    .addFooterVariable(columnaQuantity,ColumnsGroupVariableOperation.SUM)    //idem for the columnaQuantity column
    .setGroupLayout(GroupLayout.DEFAULT_WITH_HEADER)                //tells the group how to be shown, there are many
                                                                            //posibilities, see the GroupLayout class for more.
    .build();

GroupBuilder gb2 = new GroupBuilder();                                        //Create another group (using another column as criteria)
ColumnsGroup g2 = gb2.setCriteriaColumn((PropertyColumn) columnBranch)        //and we add the same operations for the columnAmount and
    .addFooterVariable(columnAmount,ColumnsGroupVariableOperation.SUM)        //columnaQuantity columns
    .addFooterVariable(columnaQuantity,ColumnsGroupVariableOperation.SUM)
    .build();

//then we add the groups to the report (through the builder)

        drb.addGroup(g1);    //add group g1
        drb.addGroup(g2);    //add group g2
```

Here the order of the group registration is important and should be consistent with the order of the registration of the columns. If we mess here, the resultant report won't have any meaning.

> **Note:** It’s important to remember that the data from the datasource must be ordered with the same criteria we want to group them.

Many more examples on **groups**: [Grouping Styles](how-to/grouping-styles.html)

## Adding charts

See examples [here](/web/20220331182701/http://dynamicjasper.com/2010/10/06/how-to-add-a-chart-new-api/) for the different char types