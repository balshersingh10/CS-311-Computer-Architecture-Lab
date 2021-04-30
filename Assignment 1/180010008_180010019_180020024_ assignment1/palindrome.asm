	.data
a:
	1234544321
	.text
main:
	load %x0, $a, %x3
	load %x0, $a, %x4
	add %x0, %x0, %x5
loop:
	beq %x3, %x0, exit
	add %x3, %x0, %x6
	divi %x6, 10, %x6
	muli %x6, 10, %x6
	sub %x3, %x6, %x7
	muli %x8, 10, %x8
	add %x8, %x7, %x8
	divi %x3, 10, %x3
	jmp loop
exit:
	beq %x8, %x4, endl
	subi %x0, 1, %x10
	end
endl:
	addi %x0, 1, %x10
	end
