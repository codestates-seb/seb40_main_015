import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import { Dispatch, SetStateAction } from 'react';

interface Props {
	merchantList: any;
	setHoverLists: Dispatch<SetStateAction<any>>;
	merchantListRef: any;
}

const MerchantLists = (props: Props) => {
	const { merchantList, setHoverLists, merchantListRef } = props;

	const navigate = useNavigate();

	const handleSearchMerchantInfo = (id: string) => {
		navigate(`/profile/merchant/${id}`);
	};

	const handleHoverMap = (location: {
		latitude: number;
		longitude: number;
	}) => {
		setHoverLists(location);
	};
	return (
		<Box>
			{merchantList?.map((item: any) => {
				const { merchantName, merchantId, location } = item;
				return (
					<List
						key={merchantId}
						onClick={() => handleSearchMerchantInfo(merchantId)}
						onMouseOver={() => handleHoverMap(location)}
						onMouseOut={() => setHoverLists({ latitude: 0, longitude: 0 })}>
						<span className="book">{merchantName}</span>
					</List>
				);
			})}
			<div ref={merchantListRef} />
		</Box>
	);
};

const Box = styled.div`
	overflow-y: scroll;
	min-height: 70px;
	max-height: 220px;
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
