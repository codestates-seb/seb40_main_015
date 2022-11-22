import useAPI from '../hooks/useAPI';

export const useMemberAPI = () => {
	const api = useAPI();

	// 회원정보
	const getMemberInfo = async (id: string) => {
		try {
			const result = await api.get(`/member/${id}`);
			console.log(result);
			return result.data;
		} catch (err) {
			return err;
		}
	};

	return { getMemberInfo };
};
