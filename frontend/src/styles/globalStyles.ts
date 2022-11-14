import { createGlobalStyle } from 'styled-components';
import gangwon from '../assets/fonts/gangwon.woff';
import kotra from '../assets/fonts/kotra.ttf';

const GlobalStyle = createGlobalStyle`
  @font-face {
        font-family: 'gangwon';
        src: local('gangwon'), local('gangwon');
        font-style: normal;
        src: url(${gangwon}) format('woff');
  }
  @font-face {
        font-family: 'kotra';
        src: local('kotra'), local('kotra');
        font-style: normal;
        src: url(${kotra}) format('truetype');
  }

  html, body, div, span, applet, object, iframe,
  h1, h2, h3, h4, h5, h6, p, blockquote, pre,
  a, abbr, acronym, address, big, cite, code,
  del, dfn, em, img, ins, kbd, q, s, samp,
  small, strike, strong, sub, sup, tt, var,
  b, u, i, center,
  dl, dt, dd, ol, ul, li,
  fieldset, form, label, legend,
  table, caption, tbody, tfoot, thead, tr, th, td,
  article, aside, canvas, details, embed, 
  figure, figcaption, footer, header, hgroup, 
  menu, nav, output, ruby, section, summary,
  time, mark, audio, video {
    margin: 0;
    padding: 0;
    border: 0;
    font-size: 10px;
    font: inherit;
    vertical-align: baseline;
    font-family: 'gangwon', 'kotra';
    background-color: #FBFBFB;
    overflow-y: auto;
    /* overflow: overlay; */
    overflow-x: hidden;
  }
  a {
    text-decoration: none;
    color: inherit;
  }
  /* HTML5 display-role reset for older browsers */
  article, aside, details, figcaption, figure, 
  footer, header, hgroup, menu, nav, section {
    display: block;
  }
  body {
    line-height: 1;
  }
  ol, ul {
    list-style: none;
  }
  blockquote, q {
    quotes: none;
  }
  blockquote:before, blockquote:after,
  q:before, q:after {
    content: '';
    content: none;
  }
  table {
    border-collapse: collapse;
    border-spacing: 0;
  }

  body::-webkit-scrollbar {
    width: 8px;
    background: transparent;
}

body::-webkit-scrollbar-thumb {
    background: rgba(0, 0, 0, 0.3);
    border-radius: 100px;
}
`;

export default GlobalStyle;
