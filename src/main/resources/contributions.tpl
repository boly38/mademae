<html>
<head>
  <title>Welcome!</title>
</head>
<body>
  <ul>
  <#list data as x>
   <li>${x_index} ${x.contributionType!"(untitled)"} ${x.dateFormat!""} (${x.age!""}) from ${x.author.pseudo!"nobody"} : ${x.contributionDetails!"(untitled)"}</li>
  </#list>
  </ul>
</body>
</html>