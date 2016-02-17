package nabo;

import java.util.Calendar;

public enum MoonPhase
{
	P_LEFT_1234,
	P_LEFT_123_RIGHT_4,
	P_LEFT_12_RIGHT_34,
	P_LEFT_1_RIGHT_234,
	P_LEFT_13_RIGHT_24,
	P_LEFT_14_RIGHT_23,
	P_LEFT_124_RIGHT_3,
	P_LEFT_134_RIGHT_2,
	P_LEFT_12_DOWN_34,
	P_LEFT_23_DOWN_14,
	P_ARC_1234,
	P_LEFTDOWN_12_RIGHTDOWN_34,
	P_LEFT_1_DOWN_234,
	P_LEFT_2_DOWN_134,
	P_SURROUND_1234,
	P_LEFT_3_DOWN_124,
	P_LEFT_4_DOWN_123,
	P_LEFT_1_UP_2_RIGHT_34,
	P_BOTTOMLEFT_4_TOPRIGHTARC_123,
	P_BOTTOMLEFT_1_TOPRIGHTARC_423,
	P_BOTTOMLEFT_2_TOPRIGHTARC_143,
	P_BOTTOMLEFT_3_TOPRIGHTARC_124,
	P_LEFT_12_TOPRIGHT_3_BOTTOMRIGHT_4,
	P_LEFT_31_DOWN_24,
	P_LEFT_13_TOPRIGHT_2_BOTTOMRIGHT_4,
	P_LEFT_32_TOPRIGHT_1_BOTTOMRIGHT_4,
	P_LEFTDOWN_32_RIGHTDOWN_14,
	P_LEFTDOWN_13_RIGHTDOWN_24,
	P_RIGHT_1234;
	
	public static MoonPhase getCurrentPhase()
	{
		int p = Helper.moonPhase(Calendar.getInstance()) - 1;
		return MoonPhase.values()[p];
	}
}