package ai;

import java.io.*;
import java.util.*;

public class Mod_N-Queens {
	
	public static int[][] inp;
	public static int wh;
	public static String type;
	public static int count = 0;
	public static int countdfs = 0;
	public static int lizards;
	static Queue<List<Node>> q = new LinkedList<>();
	static List<Node> ans = new ArrayList<>();
	
//	COMMON FUNCTIONS
	
	public static void displayanswer() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
	    
		if(type.equals("BFS")) {
			for(int i = 0;i<ans.size();i++)
				inp[ans.get(i).x][ans.get(i).y] = 1;
		}
			     
		writer.write("OK");
		writer.newLine();	     
		for(int i = 0; i < wh; i++) {
			for(int j = 0; j < wh; j++)
				
				writer.write(String.valueOf(inp[i][j]));			
			writer.newLine();
		}
	    writer.close();
	}
	
	public static boolean check(int a,int b) {
		int i = a, j = b, l = wh;
		if(inp[a][b] != 0)
			return false;
		
		while(++j<l) {
			if(inp[i][j] == 1)
				return false;
			if(inp[i][j] == 2)
				break;
		}
		i = a; j = b;
		while(--j>=0) {
			if(inp[i][j] == 1)
				return false;
			if(inp[i][j] == 2)
				break;
		}
		i = a; 
		j = b;
		
		while(++i<l) {
			if(inp[i][j] == 1)
				return false;
			if(inp[i][j] == 2)
				break;
		}
		i = a; j = b;
		while(--i>=0) {
			if(inp[i][j] == 1)
				return false;
			if(inp[i][j] == 2)
				break;
		}
		i = a; 
		j = b;
		
		while(--i>=0 && --j>=0) {
			if(inp[i][j] == 1)
				return false;
			if(inp[i][j] == 2)
				break;
		}
		i = a; j = b;
		while(++i<l && ++j<l) {
			if(inp[i][j] == 1)
				return false;
			if(inp[i][j] == 2)
				break;
		}
		i = a; j = b;
		
		while(++i<l && --j>=0) {
			if(inp[i][j] == 1)
				return false;
			if(inp[i][j] == 2)
				break;
		}
		i = a; j = b;
		while(--i>=0 && ++j<l) {
			if(inp[i][j] == 1)
				return false;
			if(inp[i][j] == 2)
				break;
		}
		
		return true;
	}
	
//	BFS SPECIFIC FUNCTIONS
	
	public static List<Node> rowcheck(Node p) {
		int x = p.x;
		int y = p.y;
		List<Node> l = new ArrayList<>();
			if(y+1==wh)
				return l;
			for(int j = y+1;j<wh;j++) {
				
				if(check(x,j)) {
					l.add(new Node(x,j));
				}
			}
			
		return l;
	}
	
	public static List<Node> colcheck(Node p) {
		int x = p.x+count;
		List<Node> l = new ArrayList<>();
			if(x+1>=wh)
				return l;
			for(int j = 0;j<wh;j++) {
				
				if(check(x+1,j)) {
					l.add(new Node(x+1,j));
					count=0;
				}
			}
		return l;
	}
	
	public static boolean solveBFS() {
		long sTime = System.currentTimeMillis();
		long eTime;
		long t;
		
		for(int i = 0; i<wh; i++) {
			for(int j = 0; j<wh; j++) {
				if(inp[i][j]!=0)
					continue;
				List<Node> a = new ArrayList<>();
				a.add(new Node(i,j));
				q.add(a);
				while(!q.isEmpty()) {
					eTime   = System.currentTimeMillis();
		    	    t = eTime - sTime;
		                            
		            if(t > 285000)
		                return false;
		            
					List<Node> list = q.poll();
					if(list.size()==lizards) {
						ans = list;
						return true;
					}
						
					for(int k = 0;k<list.size();k++) {
						inp[list.get(k).x][list.get(k).y] = 1;
					}
						
					List<Node> l = rowcheck(list.get(list.size()-1));
					
					for(int k = 0;k<l.size();k++) {
						List<Node> al = new ArrayList<>(list);
						al.add(l.get(k));
						q.add(al);
						if(al.size()==lizards) {
							ans = al;
							return true;
						}
					}
					l = colcheck(list.get(list.size()-1));
					
					for(int k = 0;k<l.size();k++) {
						List<Node> al = new ArrayList<>(list);
						al.add(l.get(k));
						
						q.add(al);
						if(al.size()==lizards) {
							ans = al;
							return true;
						}							
					}
					for(int k = 0;k<list.size();k++)
						inp[list.get(k).x][list.get(k).y] = 0;
					
					if(q.isEmpty() && (list.get(list.size()-1).x+count)<wh-1) {
						q.add(list);
						
						count++;
					}
				}				
			}
		}
		return false;
	}
	
//	DFS SPECIFIC FUNCTIONS
	
	public static boolean solveDFS(int n, int start) {
		
        for (int i = start; i < wh; i++)
        {
            if (check(i, n))
            {
                inp[i][n] = 1;
                countdfs++;
                
                if(i == wh-1) {
                	if(n != wh-1) {
                		if (solveDFS(n + 1,0) == true)
                			return true;
                	}
                	else
                		if(countdfs == lizards)
                			return true;
                }
                else {
                	if (solveDFS(n,i+1) == true)
                		return true;
                }
           
                inp[i][n] = 0; 
                countdfs--;
            }
            
            if(i == wh-1) {
            	if(n != wh-1) {
            		if (solveDFS(n + 1,0) == true)
            			return true;
            	}
            	else
            		if(countdfs == lizards)
            			return true;
            }
        }
 
        return false;	        
	}
	
//	SA SPECIFIC FUNCTIONS
	
	public static boolean solveSA() {
		int noliz = lizards;
		Random rand = new Random(); 
		int emptycount = 0;
		int trees = 0;
		
		int[][] arr = new int[wh][wh];
		for(int i = 0; i<wh; i++) {
			for(int j = 0; j<wh; j++) {
				arr[i][j] = inp[i][j];
				if(arr[i][j] == 0)
					emptycount++;
				if(arr[i][j] == 2)
					trees++;
			}
		}
		
		if(trees == wh*wh)
			return false;
		if(emptycount < noliz)
			return false;
		
		
		while(noliz > 0) {
			for(int i = 0; i < wh; i++) {
				int col = rand.nextInt(wh);
				int treecount = 0;
				
				for(int j = 0; j < wh; j++) {
					if(inp[i][j] == 2) {
						treecount++;
					}
				}
				
				if (treecount < wh) {
					while(inp[i][col] == 2)
		                col = rand.nextInt(wh);
				}
		            
				if(arr[i][col] == 0) {
					arr[i][col] = 1;
		            noliz--;
				}
		            
		        if(noliz == 0)
		             break;
			}
		}
		
		int attacks = ConflictCheck(arr);
		if(attacks == 0) {
			for(int i = 0; i<wh; i++) {
    			for(int j = 0; j<wh; j++) {
    				inp[i][j] = arr[i][j];
    			}
    		}
        	return true;
		}
		
		long sTime = System.currentTimeMillis();
		int tempattacks = attacks;
		
		long t = 0;
		double T = 500;
		double cf = 0.001;
		
		while(T > 0.001) {
            int rR = rand.nextInt(wh);
            int rC = rand.nextInt(wh); 

            while(arr[rR][rC] != 1) {
            	rR = rand.nextInt(wh);
                rC = rand.nextInt(wh);
            }
                 
            int prevR = rR;
            int prevC = rC;
            
            while(arr[rR][rC] != 0) {
            	rR = rand.nextInt(wh);
                rC = rand.nextInt(wh);
            }        
                
            arr[rR][rC] = 1;
            arr[prevR][prevC] = 0;
                            
            int nextS = ConflictCheck(arr);
            
            int deltaE = nextS - tempattacks;
            
            if(deltaE < 0)
            	tempattacks = nextS;
            else {
                if(ProbabilityCheck(T, deltaE))
                	tempattacks = nextS;
                else {
                	arr[prevR][prevC] = 1;
                    arr[rR][rC] = 0;
                }
            }
            
            T = T - cf;
            int conflicts = ConflictCheck(arr);
            
            if(conflicts == 0) {
            	for(int i = 0; i<wh; i++) {
        			for(int j = 0; j<wh; j++) {
        				inp[i][j] = arr[i][j];
        			}
        		}
            	return true;
            }
                     
            long eTime   = System.currentTimeMillis();
    	    t = eTime - sTime;
            if(t > 285000)
                return false;
		}		   
		
		return false;
	}

	private static boolean ProbabilityCheck(double t, int deltaE) {
		if(deltaE < 0)
	        return false;
	        
	    double prob = Math.exp(-deltaE/t);
	    Random rand = new Random(); 
	    
	    int uniformProb = rand.nextInt(1);
	    
	    if(uniformProb < prob)
	        return true;
	        
		return false;
	}

	private static int ConflictCheck(int[][] arr) {
		int total = 0,ra = 0,ca = 0,ll = 0,lr = 0;
		                
		int row = 0;
		while(row < wh) {
			for(int col = 0; col < wh; col++) {
				if(arr[row][col] == 1) {
					int x = row;
		            int y = col + 1;
		            while(y < wh) {
		            	if(arr[x][y] == 2)
                            break;
		            	else if(arr[x][y] == 1)
                            ca += 1;
                        y += 1;
		            }
				}
			}
            row += 1;
		}
		        
		int column = 0;
		while(column < wh) {
			for(row = 0; row < wh; row++) {
				if(arr[row][column] == 1) {
					int x = row + 1;
			        int y = column;
			        while(x < wh) {
			        	if(arr[x][y] == 2)
                            break;
			        	else if(arr[x][y] == 1)
                            ra += 1;
                         x += 1;
			        }			                   
				}	                
			}
	        column +=1; 
		}
		            		            
		for(int i = 0; i < wh; i++) {
			for(int j = 0; j < wh; j++) {
				if(arr[i][j] == 1) {
					row = i + 1;
			        int col = j - 1;
			        while(row < wh && col >= 0) {
			        	if(arr[row][col] == 2)
	                          break;
			        	else if(arr[row][col] == 1)
	                          ll += 1;
	                              
	                      row += 1;
	                      col -= 1;
			        }
				}
			}	            
		}
		            		                        
		for(int i = 0; i<wh; i++) {
			for(int j = 0; j<wh; j++) {
				if(arr[i][j] == 1) {
					row = i + 1;
			        int col = j + 1;
			        while(row < wh && col < wh) {
			        	if(arr[row][col] == 2)
	                          break;
			        	else if(arr[row][col] == 1)
	                          lr += 1;
	                              
	                      row += 1;
	                      col += 1;
			        }			                      
				}	                  
			}	                            
		}		            		    
		total = ra + ca + ll + lr;   
		return total;
	}

	
//	MAIN FUNCTION
	
	public static void main(String[] args) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
		Scanner sc = new Scanner(new FileReader("input.txt"));		
		type = sc.next();
		wh = sc.nextInt();
		lizards = sc.nextInt();
		inp = new int[wh][wh];
		for(int i = 0; i < wh; i++) {
			String line = sc.next();
			for(int j = 0; j < wh; j++)
				inp[i][j] = line.charAt(j)-48;
		}
	    
		if(type.equals("BFS")) {
			if(solveBFS())
				displayanswer();
			else
				writer.write("FAIL");
		}
		else if(type.equals("DFS")) {
			if(solveDFS(0,0))
				displayanswer();
			else
				writer.write("FAIL");
		}
		else if(type.equals("SA")) {
			if(solveSA())
				displayanswer();
			else
				writer.write("FAIL");
		}
		else {
			writer.write("FAIL");
		}
	    writer.close();
		sc.close();
	}
}

class Node {
	int x;
	int y;
	Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
