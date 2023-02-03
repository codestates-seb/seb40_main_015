const MAX_SIZE = 204_800; // 200KB
const COMP_SIZE = 51_200; // 50KB

const dataUrlFromFormFile = (file: File) => {
	return new Promise(resolve => {
		const reader = new FileReader();
		reader.onload = () => {
			resolve(reader.result);
		};
		reader.readAsDataURL(file);
	});
};

const resizeImage = (imgEl: HTMLImageElement, fileSize: number) => {
	const canvas = document.createElement('canvas');
	const ratio = Math.sqrt(fileSize / COMP_SIZE);
	let width = Math.ceil((imgEl.width /= ratio));
	let height = Math.ceil((imgEl.height /= ratio));
	canvas.width = width;
	canvas.height = height;
	canvas.getContext('2d')?.drawImage(imgEl, 0, 0, width, height);
	return canvas.toDataURL('image/jpeg');
};

const dataURLtoBlob = (dataURL: string) => {
	let byteString;
	if (dataURL.split(',')[0].indexOf('base64') >= 0)
		byteString = atob(dataURL.split(',')[1]);
	else byteString = unescape(dataURL.split(',')[1]);
	const mimeString = dataURL.split(',')[0].split(':')[1].split(';')[0];
	let ia = new Uint8Array(byteString.length);

	for (let i = 0; i < byteString.length; i++) {
		ia[i] = byteString.charCodeAt(i);
	}

	return new Blob([ia], { type: mimeString });
};

const resizeImageToBlob: (file: File) => Promise<Blob> = file => {
	return new Promise(async resolve => {
		const result = await dataUrlFromFormFile(file);
		const imgEl = document.createElement('img');
		imgEl.onload = () => {
			if (file.size < MAX_SIZE) {
				resolve(file);
			} else {
				const resizedDataUrl = resizeImage(imgEl, file.size);
				resolve(dataURLtoBlob(resizedDataUrl));
			}
		};
		imgEl.src = result as string;
	});
};

export default resizeImageToBlob;
