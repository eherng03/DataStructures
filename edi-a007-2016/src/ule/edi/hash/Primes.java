package ule.edi.hash;

public class Primes {

	private static int values[] = { 3, 7, 13, 31, 61, 127, 251, 509, 1021,
			2039, 4093, 8191, 16381, 32749, 65521, 131071, 262139, 524287,
			1048573, 2097143, 4194301, 8388593, 16777213, 33554393, 67108859,
			134217689, 268435399, 536870909, 1073741789, 2147483647 };

	//	Primeros números primos por debajo de potencias de 2, hasta 2**31
	private static int nvalues = 30;

	static int next(int c) {

		if (c < values[nvalues - 1]) {

			for (int i = 0; i < nvalues; i++) {
				if (values[i] > c) {
					return values[i];
				}
			}
		}

		throw new IllegalStateException("Overflow for 'int'");
	}

}
