<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" indent="yes" />
    
    <xsl:template match="/">
        <html>
            <head>
                <title>Persons Age Groups</title>
                <style>
                    table { width: 100%; border-collapse: collapse; }
                    th, td { border: 1px solid black; padding: 8px; text-align: left; }
                    th { background-color: #f2f2f2; }
                </style>
            </head>
            <body>
                <h2>Persons Under 18</h2>
                <table>
                    <tr>
                        <th>Driver ID</th>
                        <th>Name</th>
                        <th>Age</th>
                        <th>Birth Year</th>
                    </tr>
                    <xsl:for-each select="Persons/Person[Age &lt; 18]">
                        <xsl:sort select="Age" data-type="number" order="ascending" />
                        <tr>
                            <td><xsl:value-of select="@driverId" /></td>
                            <td><xsl:value-of select="Name" /></td>
                            <td><xsl:value-of select="Age" /></td>
                            <td><xsl:value-of select="@yearOfBirth" /></td>
                        </tr>
                    </xsl:for-each>
                </table>
                
                <h2>Persons 18 and Older</h2>
                <table>
                    <tr>
                        <th>Driver ID</th>
                        <th>Name</th>
                        <th>Age</th>
                        <th>Birth Year</th>
                    </tr>
                    <xsl:for-each select="Persons/Person[Age &gt;= 18]">
                        <xsl:sort select="Age" data-type="number" order="ascending" />
                        <tr>
                            <td><xsl:value-of select="@driverId" /></td>
                            <td><xsl:value-of select="Name" /></td>
                            <td><xsl:value-of select="Age" /></td>
                            <td><xsl:value-of select="@yearOfBirth" /></td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
