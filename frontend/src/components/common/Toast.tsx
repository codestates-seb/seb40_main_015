import { useEffect, useState } from 'react';
import styled from 'styled-components';

type ToastProps = {
	text: string;
	dismissTime?: number;
};

const Toast = ({ text, dismissTime = 3000 }: ToastProps) => {
	const [isFading, setIsFading] = useState(false);

	useEffect(() => {
		let mounted = true;
		setTimeout(() => {
			if (mounted) {
				setIsFading(true);
			}
		}, dismissTime - 500);

		return () => {
			mounted = false;
		};
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	const handleToastClick = () => {
		setIsFading(true);
	};

	return (
		<ToastWrapper onClick={handleToastClick}>
			<div className={`notification ${isFading ? 'fade-out' : ''}`}>{text}</div>
		</ToastWrapper>
	);
};

const ToastWrapper = styled.div`
	z-index: 999999;
	.notification {
		box-sizing: border-box;
		transition: transform 0.6s ease-in-out;
		background: ${props => props.theme.colors.buttonGreen};
		transition: 0.3s ease;
		border-radius: 20px;
		box-shadow: 0 0 10px rgba(0, 0, 0, 0.6);
		color: #000;
		opacity: 0.9;
		font-weight: 600;
		text-align: center;
		min-height: 50px;
		max-width: 90%;
		width: 25rem;
		color: #fff;
		padding: 15px;
		margin: 10px;
		animation: toast-in-top 0.6s;

		@media screen and (min-width: 800px) {
			animation: toast-in-right 0.6s;
		}
	}

	.fade-out {
		opacity: 0;
		transform: opacity 2s;
	}

	@keyframes toast-in-top {
		from {
			transform: translateY(-100%);
		}
		to {
			transform: translateY(0);
		}
	}

	@keyframes toast-in-right {
		from {
			transform: translateX(100%);
		}
		to {
			transform: translateY(0);
		}
	}
`;

export default Toast;
