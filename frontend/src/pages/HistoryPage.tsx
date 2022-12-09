import { useState } from 'react';
import styled from 'styled-components';
import TabLists from '../components/common/TabLists';
import Title from '../components/common/Title';
import LendBookLists from '../components/History/LendBookLists';
import RentBookLists from '../components/History/RentBookLists';
import SortButton from '../components/History/SortButton';
import useTabs from '../hooks/useTabs';

const HistoryPage = () => {
	const [tab, curTab, handleChange] = useTabs(['빌린 책', '빌려준 책']);

	//filtering
	const [filters, setFilters] = useState('');

	return (
		<Layout>
			<Title text="대여 목록" />
			<TabLists tabs={tab} handleChange={handleChange} />
			<SortButton setFilters={setFilters} />
			{curTab === '빌린 책' && <RentBookLists filters={filters} />}
			{curTab === '빌려준 책' && <LendBookLists filters={filters} />}
		</Layout>
	);
};

const Layout = styled.div`
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	background-color: #fbfbfb;

	h2 {
		font-size: 2rem;
		margin-bottom: 1rem;
	}
`;

export default HistoryPage;
