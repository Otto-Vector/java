package Monsters;

import java.util.ArrayList;
import java.util.List;

public class Elements {

	protected String Name;//���������� ����������
	protected int Atk;
	protected int Lvl;
	protected int HP;
	protected int ElAtk,ElDef;
	protected double CountHit, CountDef;
	protected int Chance;
	
	protected static final String[] Elem = {"Fire","Tree","Water","Metal","Earth"};//�������� ������� �������� ���������
	
	//�������� ��������� �������� ������
	static String DefName="Unknown";
	static int DefAtk=100;
	static int DefLvl=1;
	static int DefHP=1000;
	static int EAtk=0, EDef=0;
	static double DefCount=0;
	static int DefChance=9; //����-������� ������������ ������ (���������� �� 2 ������ �������)

	//�������� ������� ������ ���������, ��� ����������� ������ �� �� �����
	protected static List<Elements> MonstersList = new ArrayList<Elements>();
	
	public Elements()//��������� �����������, ������� ��������� �� ������
	{
		this(DefName,DefAtk,DefLvl,DefHP,EAtk,EDef,DefCount,DefCount,DefChance);
	}
	
	public Elements(String Name,int ElAtk,int ElDef)//�����������, ������� �� ���������� � ���������
	{
		this(Name,DefAtk,DefLvl,DefHP,ElAtk,ElDef,DefCount,DefCount,DefChance);
	}
	
	//������ �����������
	private Elements(String Name, int Atk, int Lvl, int HP, int ElAtk, int ElDef, double CountHit, double CountDef, int Chance)
	{
		this.Name=Name;
		this.Atk=Atk;
		this.Lvl=Lvl;
		this.HP=HP;
		this.ElAtk=ElAtk;
		this.ElDef=ElDef;
		this.CountHit=CountHit;
		this.CountDef=CountDef;
		this.Chance=Chance;
		
		MonstersList.add(this); //���������� �������� � ������ ��� ������ ������������
	}
	
	public void ShowMonster() //������� ������ ������ �������
 
    {
	if (this.HP>0)	//�������� ������ "=" ��� ������ ������� � ������������� HP
    	System.out.printf("=���:(%10s) �������:(%2d) ��: %4d; �����: (%5s) ������:(%5s) :: %2d-���/%d/, %2d-������/%d/\n",
			this.Name,this.Lvl,this.HP,Elem[(this.ElAtk)],Elem[(this.ElDef)],
				(int)this.CountHit,(int) ((this.CountHit*100)%100),(int)this.CountDef,(int) ((this.CountDef*100)%100));
    else System.out.printf(" ���:(%10s) �������:(%2d) ��: %4d; �����: (%5s) ������:(%5s) :: %2d-���/%d/, %2d-������/%d/\n",
			this.Name,this.Lvl,this.HP,Elem[(this.ElAtk)],Elem[(this.ElDef)],
				(int)this.CountHit,(int) ((this.CountHit*100)%100),(int)this.CountDef,(int) ((this.CountDef*100)%100));
    }
	
    public static void ShowMonstersList()
    { for (Elements M : MonstersList) M.ShowMonster(); //�������� ������ ��� ������ ������ �� ������� MonsterList
    System.out.println();
    }
    
    public boolean Chancer (int CH) //����� ������������ ������ � ������� ���������� ��� ��� ����������� �� �����
    {
    	int rnd = (int) (Math.random()*101);
    	if (rnd<CH)
    	{
    		System.out.print(rnd+"."+CH+"=�������� �����!");
    		return true;
    	}
    	else return false;
    }
    
    public static void lvlUp (Elements V) //����� �������� ������
    {
    		V.HP+=130; V.Atk*=1.15; V.Lvl++;
    		V.Chance+=2;
    }
    
    public static int Attack (Elements N1, Elements N2) //����� ����� � ������ ���������� ������ ���������
    {
    	byte A=0; //�������� ���������� ��� �������� ������������ ������ ��� ����� �� �������
    	int hp=N2.HP; //�������� ���������� � ����������� HP �� �����
    	int kick=N1.Atk; //���������� �������� ����� �����������
    	    	
    	N1.CountHit++; N2.CountDef++; //������� ������ � �������� �����������
    
    if	(N1.ElDef==3 && N1.Chancer(N1.Chance)) {kick*=1.21; System.out.print("+��-");} //���� ���������� ����� ��� �������� ����� � ������ ����
    if	(N2.ElDef==2 && N2.Chancer(N2.Chance/2)) {kick=1; System.out.print("+��-");} //���� ������� �� ����� ��� �������� ���� � ������ ����
    if	(N2.ElDef==4 && N2.Chancer(N2.Chance)) {kick*=0.6; System.out.print("+��-");} //���� ��������� ������ �� ����� ��� �������� ����� � ������ ����

    	int hit=N1.ElAtk-N2.ElDef; //�������� ���������� ������� ���������
    	if  (hit == -1 || hit == 4) //�������� ��������� ���� �� ������ � ������� �� ����������, ������� ������� -1 � 4 ��� ������
    			{
    			N2.HP -= kick*1.2;// (���������� ��������� ����������� ��� ���������� ��������)
    			N1.CountHit+=0.01; //c������ ����������� ������, ������� ����� ����������� �� ������
    			A=1; //��� �������� �������������� ��� ������������ ����.����� �� ��������
    			}
   		
    	else if (hit == 1 || hit == -4) // �� �� ����� � � �������� ������� �����. ��������� (����) �� 0 �� 4 � �������...
    	 		{
    			 N2.HP -= kick*0.8; 		// ���������� ��������� ����������� ��� ����������� �������� ������ ����� ��������� ���������
    	 		 N2.CountDef+=0.01; //������� ������ ��� ������������ ��������
    	 		 A=-1; //��� �������� �������������� ��� ������������ ������ ��������
    	 		 }
    	else 	{
    			N2.HP -= kick; //��� ��������� ������������� ������������ ��������� ������� ����
    			}
   		
    if(N1.ElDef==1 && N1.Chancer(N1.Chance*2)) {N1.HP+=(hp-N2.HP)*0.35; System.out.print("+��-");} //���� ������� ������, ���� ��������� 40% �������� ��� ����� ��
    		 
    			return A; //���������� �������� 0, ���� �� �������� �� ���� �������
    }
    	
    public int getHP() {
    	return HP;
	}
    
    public int getAtk() {
    	return Atk;
	}
    
    public int getLvl() {
    	return Lvl;
	}
    public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getElAtk() {
		return Elem[ElAtk];
	}

	public void setElAtk(int elAtk) {
		ElAtk = elAtk;
	}

	public String getElDef() {
		return Elem[ElDef];
	}

	public void setElDef(int elDef) {
		ElDef = elDef;
	}
	
	public double getCountHit() {
		return CountHit;
	}

	public double getCountDef() {
		return CountDef;
	}

	public int getChance() {
		return Chance;
	}
	
	
    
}
