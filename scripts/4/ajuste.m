clc
clear all
close all

densidad = [0,1,2,3,4,5,6,7];
radios = [0.05,0.075,0.1,0.125,0.15];

%datos simulacion
y = [0.95,0.690806119,0.475543112,0.370089607,0.318491324,0.280430758,0.255742179,0.229277063;0.95,0.701600785,0.451332296,0.357936552,0.299078346,0.261498389,0.233325739,0.206000159;0.95,0.718903304,0.475100281,0.343938609,0.28489174,0.238724682,0.20714668,0.176650288;0.95,0.70296828,0.474265038,0.32699809,0.262854438,0.211357192,0.172718005,0.141892083;0.95,0.734274923,0.466883332,0.305107189,0.236457592,0.174687256,0.136401449,0.099593108];

%datos modelo
d = [0.915,0.61,0.441,0.333,0.284,0.264,0.243,0.208];

error = 0; 
for i = 1:5
 c = y(i,:);
  for j = 1:8
   error += (d(j) - c(j))^2;
  endfor
  e(i,1) = error;
  error = 0;
endfor

%errores = [0.10236,0.082133,0.052071,0.041124,0.034087];

figure(1)
for i = 1:5
plot(radios,e(:,1),'m.', "markersize", 70)
xlabel('Radios [m]');
ylabel('Error');
%title('Error entre DCM y las distintas pendientes planteadas')
hold on 
endfor
axis([0.04 0.16 0 0.06]);
xbounds = xlim();
set(gca, 'xtick', xbounds(1):0.01:xbounds(2));
ybounds = ylim();
set(gca, 'ytick', ybounds(1):0.01:ybounds(2));
set(gca, 'FontSize', 50)

figure(2)
for i = 1:5
index = find(e == min(e));
plot(densidad,y(index,:),'b-')
xlabel('Densidad [1/m^2]')
ylabel('Velocidad media [m/s]')
%title('Grafico de la funcion con menor error vs. DCM ')
hold on
endfor
figure(2)
plot(densidad,d,'r-')
h1 = legend ("Rmin = 0.075m","Predtechenskii and Milinskii");
set(gca, 'FontSize', 50)
set (h1,,"fontsize", 50); 

predt_d = [0.088,0.177,0.354,0.531,0.708,0.885,1.062,1.239,1.416,1.593,1.77,1.947,2.124,2.301,2.478,2.655,2.832,3.009,3.186,3.363,3.54,3.717,3.894,4.071,4.248,4.425,4.602,4.779,4.956,5.133,5.31,5.487,5.664,5.841,6.018,6.195,6.372,6.549,6.726,6.903,7.08,7.257,7.434,7.611,7.788,7.965,8.142];
predt_v = [0.915,0.881,0.817,0.758,0.704,0.655,0.61,0.569,0.532,0.498,0.468,0.441,0.417,0.396,0.377,0.36,0.345,0.333,0.321,0.312,0.303,0.296,0.29,0.284,0.279,0.275,0.271,0.268,0.264,0.261,0.258,0.255,0.251,0.247,0.243,0.239,0.234,0.228,0.222,0.215,0.208,0.2,0.191,0.182,0.172,0.162,0.151];

v2 = [0.95,0.701600785,0.451332296,0.357936552,0.299078346,0.261498389,0.233325739,0.206000159];
e2 = [0,0.059054984,0.041125278,0.032955848,0.027407527,0.025089887,0.024152226,0.022135052];

figure(3)
scatter(densidad,y(index,:),225, "filled");
j1 = errorbar(densidad,y(index,:),e2,"m.");
set (j1, "markersize", 70, "linewidth", 3);
hold on;
scatter(predt_d,predt_v, 225,"b", "filled");
hold on;
xlabel('Densidad [1/m^2]')
ylabel('Velocidad media [m/s]')
h2 = legend ("Rmin = 0.075m","Predtechenskii and Milinskii");
xlim([0 9]);
set (h2,"fontsize", 50); 
set(gca, 'FontSize', 50)
