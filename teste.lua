a = tonumber(read())
b = 0
ops = { ["+"] = "add", ["-"] = "sub", ["*"] = "mul", ["/"] = "div" }

if a > 10 then
print("maior que 10")

elseif a < 10 then
    print("menor que 10")

    stats = {[0] = 20, [1] = 60, [2] = 30}
    for symbol, op in ops do
        stats[1] = 40
        print(stats[1])
        print(ops["/"])
    end 
else
    for i=0, 10 do

        if b == 5 then
            print("Primeira metade >:)")
        else
        print(b)
    end
    b = b + 1

    end
end