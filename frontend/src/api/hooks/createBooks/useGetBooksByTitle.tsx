import { useQuery } from '@tanstack/react-query';
import { getBooksByTitle } from '../../createBook';

const useGetBooksByTitle = (searchText: string) =>
	useQuery(['books'], () => getBooksByTitle(searchText), {
		refetchOnWindowFocus: false,
		enabled: false,
	});

export default useGetBooksByTitle;
