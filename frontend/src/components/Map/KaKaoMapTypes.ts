export interface MarkerProps {
	merchantCount?: number;
	totalBookCount?: number;
	sector?: number;
	location: {
		latitude: number;
		longitude: number;
	};
}

export interface MerchantSectorList {
	merchantCount: number;
	sector: number;
	location: { latitude: number; longitude: number };
}

export interface MerchantSectorProps {
	merchantCount?: number;
	totalBookCount?: number;
	sector: number;
	location: {
		latitude: number;
		longitude: number;
	};
}

export interface SizeProps {
	width: number;
	height: number;
}

export interface SelectOverlay {
	merchantCount?: number;
	bookCount?: number;
	sector: number;
	location: {
		latitude: number;
		longitude: number;
	};
}

export interface MerchantList {
	merchantId: number;
	merchantName: string;
	location: {
		latitude: number;
		longitude: number;
	};
}
