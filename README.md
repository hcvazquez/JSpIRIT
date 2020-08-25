
![image](https://github.com/hcvazquez/JSpIRIT/blob/master/img/LogoJSpIRIT.png)

<div><b><font size="4">Code Smells Identification and Prioritization &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</font></b></div>

<div>JSpIRIT&nbsp;supports the identification of 10 code smells following the detection strategies presented by Lanza and Marinescu*:</div>

<ul><li><font face="SFRM1095"><span style="font-size:15px;line-height:22px">Brain Class&nbsp;</span></font></li>
<li><font face="SFRM1095"><span style="font-size:15px;line-height:22px">Brain Method&nbsp;</span></font></li>
<li><font face="SFRM1095"><span style="font-size:15px;line-height:22px">Data Class&nbsp;</span></font></li>
<li><font face="SFRM1095"><span style="font-size:15px;line-height:22px">Disperse Coupling&nbsp;</span></font></li>
<li><font face="SFRM1095"><span style="font-size:15px;line-height:22px">Feature Envy&nbsp;</span></font></li>
<li><font face="SFRM1095"><span style="font-size:15px;line-height:22px">God Class&nbsp;</span></font></li>
<li><font face="SFRM1095"><span style="font-size:15px;line-height:22px">Intensive Coupling&nbsp;</span></font></li>
<li><font face="SFRM1095"><span style="font-size:15px;line-height:22px">Refused Parent Bequest&nbsp;</span></font></li>
<li><font face="SFRM1095"><span style="font-size:15px;line-height:22px">Shotgun Surgery&nbsp;</span></font></li>
<li><font face="SFRM1095"><span style="font-size:15px;line-height:22px">Tradition Breaker</span></font></li></ul>
<p><span style="font-size:15px;line-height:22px;font-family:SFRM1095;background-color:transparent"><span>&nbsp;&nbsp; &nbsp;</span>To prioritize the code smells JSpIRIT provides several kind of rankings that use different criteria such as the history of the application, the relevance of the kind of code smell, or modifiability scenarios.</span></p>
<p><span style="font-size:15px;line-height:22px;font-family:SFRM1095;background-color:transparent">*</span><span style="background-color:transparent;font-size:1em">Michele Lanza and Radu Marinescu. Object-Oriented Metrics in Practice - Using Software Metrics to Characterize, Evaluate, and Improve the Design of Object-Oriented Systems. Springer, 2006.</span></p>
<p><span style="line-height:22px;font-family:SFRM1095;background-color:transparent"><b><font size="4">Agglomerations Identification and Prioritization</font></b></span></p>
<p><span style="font-family:SFRM1095;font-size:15px">JSpIRIT also supports the identification of agglomerations of code smells.&nbsp;</span><span style="background-color:transparent;font-size:1em">&nbsp;Agglomerations are groups of inter-related code smells (e.g., syntactically-related code smells within a component) that likely indicate together the presence of an architectural problem. Currently, JSpIRIT supports 2 kind of agglomerations:</span></p>
<ul><li>Smells within a component. This grouping pattern identifies code smells that are implemented by the same architectural component. Specifically, we look for one single component with: (i) code smells that are syntactically related, or (ii) code elements infected by the same type of code smell. Two classes are syntactically related if at least one of them references the other one.</li>
<li>Smells in a hierarchy. This grouping pattern identifies code smells that occur across the same inheri- tance tree involving one or more components. We only consider hierarchies exhibiting the same type of code smell. The rationale is that a recurring introduction of the same smell in different code elements might represent a bigger problem in the hierarchy.&nbsp;</li></ul>
<div><br>
</div>
<p><span style="line-height:22px;font-family:SFRM1095;background-color:transparent"><font size="4"><b>Getting Started with JSpIRIT</b></font></span></p>
<p><font face="SFRM1095"><span style="font-size:15px;line-height:22px"><span>&nbsp;&nbsp; &nbsp;</span>This section has brief instructions for getting started with JSpIRIT.</span></font></p>
<p><span style="line-height:22px;font-family:SFRM1095;background-color:transparent"><font size="4">How to install</font></span></p>
<p><span style="background-color:transparent;font-size:15px;line-height:22px"><font face="SFRM1095"><span>&nbsp;&nbsp; &nbsp;</span>You can download a jar file containing the plugin from </font></span><a href="https://sites.google.com/site/santiagoavidal/projects/jspirit/SpIRIT_1.0.0.201410081128.jar?attredirects=0&amp;d=1" style="background-color:rgb(255,255,255)">here</a>&nbsp;(<a href="https://sites.google.com/site/santiagoavidal/projects/jspirit/SpIRIT_1.0.0.201501111132-Agglomerations.jar?attredirects=0&amp;d=1">Agglomeration version</a>)<span style="background-color:transparent;font-size:15px;line-height:22px"><font face="SFRM1095">.&nbsp;</font></span><span style="background-color:transparent;font-size:15px;line-height:22px"><font face="SFRM1095">Download it into the Eclipse dropins folder and restart Eclipse.</font></span></p>
<p><span style="background-color:transparent;font-size:15px;line-height:22px"><font face="SFRM1095"><span>&nbsp; &nbsp; JSpIRIT has been tested in&nbsp;</span></font></span><span style="font-size:15px;line-height:22px;font-family:SFRM1095;background-color:transparent">Eclipse Kepler (v4.3.2) with&nbsp;</span><span style="font-size:15px;line-height:22px;font-family:SFRM1095;background-color:transparent">Java 1.7.</span></p>
<p><span style="font-family:SFRM1095;font-size:large;line-height:22px;background-color:transparent">How to</span><span style="font-family:SFRM1095;font-size:large;line-height:22px;background-color:transparent">&nbsp;find and rank code smells</span></p>
<p><span style="font-size:15px;line-height:22px;font-family:SFRM1095;background-color:transparent">&nbsp; &nbsp; To identify and rank code smells,&nbsp;</span><span style="background-color:transparent"><font face="SFRM1095"><span style="font-size:15px;line-height:22px">right click on the project to analyze and from the popup menu select "JSpIRIT-&gt;Find Code Smells".&nbsp;The "JSpIRIT View" will indicate the progress of the smells identification. When it's all done,&nbsp;the&nbsp;</span></font></span><span style="font-family:SFRM1095;font-size:15px;line-height:22px;background-color:transparent">"JSpIRIT View"</span><span style="font-family:SFRM1095;font-size:15px;line-height:22px;background-color:transparent">&nbsp; will list the smells and their ranking values.&nbsp;</span></p>
<div style="display:block;text-align:left"></div>
<div style="display:block;text-align:left"><a href="https://lh6.googleusercontent.com/AhVLNouSCpr04xmWjet2PbafwX5IPGYO4Ox_QI8ANn09s74Sa64264JzTw-Gs-u37yYMFIVJVfiWStPJqGp1C5ncvybdOqnEuVT_JVcTj_UpSssU3Dw=w1280" imageanchor="1"><img border="0" src="https://lh6.googleusercontent.com/AhVLNouSCpr04xmWjet2PbafwX5IPGYO4Ox_QI8ANn09s74Sa64264JzTw-Gs-u37yYMFIVJVfiWStPJqGp1C5ncvybdOqnEuVT_JVcTj_UpSssU3Dw=w1280"></a></div>
<br>
<p><span style="font-family:SFRM1095;font-size:large;line-height:22px">How to configure the ranking</span></p>
<p><span>&nbsp; &nbsp; Once the code smells are identified, you can select the way in which the ranking is calculated.&nbsp;</span></p>
<ol><li><span style="background-color:transparent;font-size:1em;line-height:1.5">&nbsp; &nbsp;&nbsp;Click in the gear icon (<a href="https://sites.google.com/site/santiagoavidal/projects/jspirit/configuration.png?attredirects=0" imageanchor="1" style="font-size:1em;line-height:1.5"><img alt="icon" border="0" src="https://sites.google.com/site/santiagoavidal/projects/jspirit/configuration.png"></a>) of the&nbsp;</span><span style="background-color:transparent;font-family:SFRM1095;font-size:15px;line-height:22px">"JSpIRIT View"</span></li>
<li><span style="background-color:transparent;font-family:SFRM1095;font-size:15px;line-height:22px">&nbsp; &nbsp;&nbsp;</span><span style="background-color:transparent;font-family:SFRM1095;font-size:15px;line-height:22px">Select the kind of ranking</span></li>
<li><span style="background-color:transparent;font-size:1em;line-height:1.5">&nbsp; &nbsp; Configure the criteria of the ranking</span></li>
</ol>
<div>
<div style="display:block;text-align:left"><a href="https://sites.google.com/site/santiagoavidal/projects/jspirit/Captura%20de%20pantalla%202014-05-13%20a%20la%28s%29%2011.28.23.png?attredirects=0" imageanchor="1"><img border="0" src="https://sites.google.com/site/santiagoavidal/projects/jspirit/Captura%20de%20pantalla%202014-05-13%20a%20la%28s%29%2011.28.23.png"></a></div>
<br>
</div>
</div>
</div>
<div><span style="font-family:SFRM1095"><font size="4"><b>Presentation SATURN 2016</b></font></span></div>
<div><span style="font-family:SFRM1095"><font size="4"><b><br>
</b></font></span></div>
<div><font face="verdana, sans-serif" size="2"><a href="https://sites.google.com/site/santiagoavidal/projects/jspirit/presentation-saturn2016.pdf?attredirects=0&amp;d=1">Slides</a></font></div>
<div><br>
</div>
<div><b>Video</b>:
<div><img src="https://www.google.com/chart?chc=sites&amp;cht=d&amp;chdp=sites&amp;chl=%5B%5BV%C3%ADdeo+de+YouTube'%3D20'f%5Cv'a%5C%3D0'10'%3D479'0'dim'%5Cbox1'b%5CF6F6F6'fC%5CF6F6F6'eC%5C0'sk'%5C%5B%22V%C3%ADdeo+de+YouTube%22'%5D'a%5CV%5C%3D12'f%5C%5DV%5Cta%5C%3D10'%3D0'%3D480'%3D267'dim'%5C%3D10'%3D10'%3D480'%3D267'vdim'%5Cbox1'b%5Cva%5CF6F6F6'fC%5CC8C8C8'eC%5C'a%5C%5Do%5CLauto'f%5C&amp;sig=8YFd2qCpu-UeUefoLuEphG43D3o" data-origsrc="XLKjEsCdWiA" data-type="youtube" data-props="align:left;borderTitle:Vídeo de YouTube;height:270;showBorder:true;showBorderTitle:false;width:480;" width="480" height="270" style="display:block;text-align:left;margin-right:auto;"></div>
</div>
<div><br>
</div>
<div><br>
</div>
<div><span style="font-family:SFRM1095"><font size="4"><b>Related Publications</b></font></span></div>
<div><span style="font-family:SFRM1095"><font size="4"><b><br>
</b></font></span></div>
<div><span style="font-family:SFRM1095;font-size:large">Journals</span></div>

</div>
<blockquote style="margin:0 0 0 40px;border:none;padding:0px">
<div title="Page 13"><li><font color="#000000" face="Verdana, Arial, Helvetica, sans-serif"><span style="line-height:normal">Santiago Vidal, Claudia Marcos, and Andrés Díaz-Pace. An approach to prioritize code smells for refactoring. Automated Software Engineering (2014): 1-32.</span></font></li>
</div>
</blockquote>
<div title="Page 13">
<div><span style="font-family:SFRM1095;font-size:large">Conferences</span></div>
</div>
<blockquote style="margin:0 0 0 40px;border:none;padding:0px">
<div title="Page 13"><li><font color="#000000"><font face="Verdana, Arial, Helvetica, sans-serif"><span style="line-height:normal">Santiago Vidal, Everton Guimaraes,&nbsp;William Oizumi,&nbsp;Alessandro Garcia, Andrés Díaz-Pace, Claudia Marcos.&nbsp;</span></font>On the Criteria for Prioritizing Code Anomalies to Identify Architectural Problems in&nbsp;Proceedings of the 31st Annual ACM Symposium on Applied Computing (SAC '16), ACM, Pisa, Italy, 2016.</font></li>
</div>
<div title="Page 13"><li><font color="#000000" face="Verdana, Arial, Helvetica, sans-serif"><span style="line-height:normal">Santiago Vidal, Hernán Vazquez, Andrés Díaz-Pace, Claudia Marcos, Alessandro Garcia, William Oizumi. JSpIRIT: a flexible tool for the analysis of code smells in 2015 34th International Conference of the Chilean Computer Science Society (SCCC), IEEE, Santiago, Chile, November, 2015.</span></font></li>
</div>
</blockquote>
<div><br>
</div>
<div>
<div><font face="SFRM1095" size="4"><b>Future Improvements</b></font></div>
</div>
<blockquote style="margin:0 0 0 40px;border:none;padding:0px"><font color="#333333">Currently, we are working in new features for JSpIRIT. Some of them involve:</font><br>
</blockquote>
<blockquote style="margin:0 0 0 40px;border:none;padding:0px">
<ul><li><font color="#333333">Automatic refactoring of code smells</font></li>
<li><font color="#333333">Identification of code smells for&nbsp;performance&nbsp;</font></li></ul>
</blockquote>
<div><font face="SFRM1095" size="4"><b><br>
</b></font></div>
