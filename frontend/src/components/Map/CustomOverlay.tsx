import { Dispatch, SetStateAction, useEffect, useState } from 'react';
import { CustomOverlayMap } from 'react-kakao-maps-sdk';
import styled from 'styled-components';

interface MarkerProps {
	merchantCount?: number;
	bookCount?: number;
	sector: number;
	location: {
		latitude: number;
		longitude: number;
	};
}

const CustomOverlay = ({
	sector,
	selectOverlay,
	setSelectOverlay,
	centerCoord,
	current,
	zoomLevel,
}: {
	sector: MarkerProps[];
	selectOverlay: any;
	setSelectOverlay: Dispatch<SetStateAction<any>>;
	centerCoord: any;
	current: any;
	zoomLevel: number;
}) => {
	const [active, setActive] = useState<number>(-1);

	const handleClickSector = (item: any) => {
		if (selectOverlay !== item) {
			setSelectOverlay(item);
		} else {
			setSelectOverlay(null);
		}
	};

	const handleToggleActive = (idx: number) => {
		if (idx === active) {
			setActive(-1);
		} else {
			setActive(idx);
		}
	};

	useEffect(() => {
		handleToggleActive(-2);
	}, [centerCoord, current, zoomLevel]);

	return (
		<>
			{sector.map((item: MarkerProps, idx: number) => {
				const { merchantCount, location, bookCount } = item;
				return (
					<div
						key={`${location.latitude}-${location.longitude}`}
						onClick={() => handleClickSector(item)}>
						<CustomOverlayMap
							position={{
								lat: Number(location.latitude) + 0.0001,
								lng: Number(location.longitude),
							}}
							clickable={true}>
							<StyledOverlay
								count={merchantCount || bookCount}
								className={idx === +active ? ' active' : ''}
								onClick={() => handleToggleActive(idx)}>
								{merchantCount || bookCount}
							</StyledOverlay>
						</CustomOverlayMap>
					</div>
				);
			})}
		</>
	);
};

interface StyledOverlayProps {
	count?: number;
}

const StyledOverlay = styled.div<StyledOverlayProps>`
	width: ${props =>
		props?.count &&
		(props.count < 10
			? '5rem'
			: props.count < 100
			? '6rem'
			: props.count < 1000
			? '7rem'
			: '8rem')};
	height: ${props =>
		props?.count &&
		(props.count < 10
			? '5rem'
			: props.count < 100
			? '6rem'
			: props.count < 1000
			? '7rem'
			: '8rem')};
	font-size: ${props =>
		props?.count &&
		(props.count < 10
			? '2rem'
			: props.count < 100
			? '2.5rem'
			: props.count < 1000
			? '2.7rem'
			: '3rem')};
	background-color: rgba(38, 121, 93, 0.8);
	cursor: pointer;
	border: 1px solid rgb(18, 75, 56);
	border-radius: 54px;
	color: white;
	display: flex;
	justify-content: center;
	align-items: center;
	:hover {
		background-color: white;
		color: rgb(18, 75, 56);
		border: 2px solid rgb(18, 75, 56);
	}
	&.active {
		background-color: white;
		color: rgb(18, 75, 56);
		border: 2px solid rgb(18, 75, 56);
	}
`;

export default CustomOverlay;
