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
	return canvas;
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
				resizedDataUrl.toBlob(blob => blob && resolve(blob), 'image/webp');
			}
		};
		imgEl.src = result as string;
	});
};

export default resizeImageToBlob;
