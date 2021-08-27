package interfaceList;

import annotations.Log;

public interface interfaceTest {
    @Log
    void calculation(int param1);

    void calculation(int param1, int param2);

    @Log
    void calculation(int param1, int param2, String param3);

    @Log
    void calculationCommon(Object ...param);
}
