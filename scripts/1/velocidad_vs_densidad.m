densidad = [1,2,3,4,5];

v1 = [1.515910723,0.901484572,0.71901595,0.607788575,0.530766283];
e1 = [0.164164528,0.063117684,0.046927812,0.035970843,0.034529587];

v3 = [1.362326836,0.917232966,0.688207558,0.571498808,0.480730707];
e3 = [0.122355629,0.066491124,0.049723485,0.039367948,0.033207935];

v5 = [1.392881804,0.911534561,0.647916549,0.516114033,0.407110962];
e5 = [0.113199319,0.071591442,0.047003411,0.040546837,0.033592143];

scatter(densidad,v1, "markersize", 15, "filled");
j1 = errorbar(densidad,v1,e1,".");
set (j1, "markersize", 50, "linewidth", 3);
hold on;

scatter(densidad,v3, "markersize", 15, "filled");
j3 = errorbar(densidad,v3,e3,".");
set (j3, "markersize", 50, "linewidth", 3);
hold on;

scatter(densidad,v5, "markersize", 15, "filled");
j5 = errorbar(densidad,v5,e5,".");
set (j5, "markersize", 50, "linewidth", 3);
hold on;

%axis([0.13 0.27 0 800]);
%xbounds = xlim();
%set(gca, 'xtick', xbounds(1):0.01:xbounds(2));
%ybounds = ylim();
%set(gca, 'ytick', ybounds(1):50:ybounds(2));

xlabel("Densidad [1/m^2]", "fontsize", 50);
ylabel("Velocidad media [m/s]", "fontsize", 50);
set(gca, "linewidth", 1, "fontsize", 50);
