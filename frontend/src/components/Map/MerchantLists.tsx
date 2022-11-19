import styled from 'styled-components';

interface Props {
	merchantList: any;
}

const MerchantLists = (props: Props) => {
	const { merchantList } = props;
	return (
		<>
			{merchantList?.content.map((item: any) => {
				const { merchantName, merchantId } = item;
				return (
					<List key={merchantId}>
						<div className="book"> {merchantName}</div>
					</List>
				);
			})}
		</>
	);
};

const List = styled.div`
	padding-top: 30px;
	padding-bottom: 30px;
	display: flex;
	align-items: center;
	justify-content: center;
	background-color: white;
	border-bottom: 0.5px solid rgb(196, 182, 186);
	:hover {
		background-color: ${props => props.theme.colors.background};
	}
`;

export default MerchantLists;
