export interface MemberInfo {
	memberId: number;
	name: string;
	location: {
		lat: string;
		lon: string;
	} | null;
	address: string | null;
	totalBookCount: number;
	avatarUrl: string | null;
	avgGrade: number;
}
