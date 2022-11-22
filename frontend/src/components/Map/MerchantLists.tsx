import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';

interface Props {
	merchantList: any;
}

const MerchantLists = (props: Props) => {
	const { merchantList } = props;
	const navigate = useNavigate();
	// const handleSearchMerchantInfo = (id: string) => {
	// 	navigate(`/profile/${id}`);
	// };
	return (
		<Box>
			{merchantList?.content.map((item: any) => {
				const { merchantName, merchantId } = item;
				return (
					<List key={merchantId}>
						<div className="book"> {merchantName}</div>
					</List>
				);
			})}
		</Box>
	);
};

const Box = styled.div`
	overflow-y: scroll;
	height: 220px;
`;

const List = styled.div`
	padding-top: 30px;
	padding-bottom: 30px;
	display: flex;
	align-items: center;
	justify-content: center;
	background-color: #fbfbfb;
	border-bottom: 0.5px solid rgb(196, 182, 186);
	:hover {
		background-color: ${props => props.theme.colors.grey};
	}
`;

export default MerchantLists;
