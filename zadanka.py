import decimal
import numpy


def postcodes_generator(smaller_postcode, postcode):
    postcode_list = []

    pc1_0 = int(smaller_postcode.split("-")[0])
    pc1_1 = int(smaller_postcode.split("-")[1])
    pc2_0 = int(postcode.split("-")[0])
    pc2_1 = int(postcode.split("-")[1])

    while pc1_0 <= pc2_0:
        if pc1_0 == pc2_0 and pc1_1 >= pc2_1:
            break
        if pc1_1 < 1000:
            if 0 <= pc1_1 < 10:
                postcode_list.append(str(pc1_0) + "-00" + str(pc1_1))
            elif 10 <= pc1_1 < 100:
                postcode_list.append(str(pc1_0) + "-0" + str(pc1_1))
            else:
                postcode_list.append(str(pc1_0) + "-" + str(pc1_1))
            pc1_1 += 1
        else:
            pc1_0 += 1
            pc1_1 = 0

    return postcode_list


def check_empty_fields(exist_fields, range_list):
    #f(x) for x in sequence if condition
    new_list = [i for i in range(range_list+1) if i not in exist_fields]
    return new_list


def decimal_list(beginnig, end, step):
    new_list = [decimal.Decimal(i) for i in numpy.arange(beginnig, end, step)]
    return new_list


funcion_number1 = postcodes_generator("79-900", "80-155")
for item in funcion_number1:
    print(item)

funcion_number2 = check_empty_fields([2,3,7,4,9], 10)
for item in funcion_number2:
    print(item)

function_number3 = decimal_list(2.5, 5, 0.5)
for i in function_number3:
    print(i)
    print(type(i))
