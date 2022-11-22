import useAPI from '../hooks/useAPI';

export const useMemberAPI = () => {
	const api = useAPI();
	// 회원정보
	const getMemberInfo = (id: string | undefined) =>
		api.get(`/member/${id}`).then(res => res.data);

	return { getMemberInfo };
};
