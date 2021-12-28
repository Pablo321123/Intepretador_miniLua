
ops = { ["+"] = "add", ["-"] = "sub", ["*"] = "mul", ["/"] = "div" }

stats = {}
for symbol, op in ops do
 stats[op] = 0
end

exp = {}

print("Entre com uma expressao:")
repeat
 v = read(" Proximo elemento: ")
 if v ~= "" then
    exp[#exp+1] = v
 end
until v == ""

-- Calculo passo a passo da expressao dada.
print("Operacoes:")
res = exp[1]
for i=2,#exp-1,2 do
 op = ops[exp[i]]
 next = tonumber(exp[i+1]) or 0
 if op then
 if op == "add" then
 tmp = res + next
 elseif op == "sub" then
 tmp = res - next
 elseif op == "mul" then
 tmp = res * next
 elseif op == "div" then
 tmp = res / next
 end 

 print("" .. op .. "(" .. res .. ", " .. next .. "): " .. tmp)
 res = tmp
 stats[op] = stats[op] + 1
 else
 print(" ???(" .. res .. ", " .. next .. "): erro")
 res = 0
 end
end

print()
print("Estatisticas:")
print("" .. stats.add .. (stats.add == 1 and " adicao" or " adicoes"))
print("" .. stats.sub .. (stats.sub == 1 and " substracao" or " subtracoes"))
print("" .. stats.mul .. (stats.mul == 1 and " multiplicacao" or " multiplicacoes"))
print("" .. stats.div .. (stats.div == 1 and " divisao" or " divisoes")) 