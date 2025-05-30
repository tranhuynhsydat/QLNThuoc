USE [master]
GO
/****** Object:  Database [QLNThuoc]    Script Date: 4/24/2025 2:09:03 PM ******/
CREATE DATABASE [QLNThuoc]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'QLNThuoc', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\QLNThuoc.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'QLNThuoc_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\QLNThuoc_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [QLNThuoc] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [QLNThuoc].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [QLNThuoc] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [QLNThuoc] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [QLNThuoc] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [QLNThuoc] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [QLNThuoc] SET ARITHABORT OFF 
GO
ALTER DATABASE [QLNThuoc] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [QLNThuoc] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [QLNThuoc] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [QLNThuoc] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [QLNThuoc] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [QLNThuoc] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [QLNThuoc] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [QLNThuoc] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [QLNThuoc] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [QLNThuoc] SET  DISABLE_BROKER 
GO
ALTER DATABASE [QLNThuoc] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [QLNThuoc] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [QLNThuoc] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [QLNThuoc] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [QLNThuoc] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [QLNThuoc] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [QLNThuoc] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [QLNThuoc] SET RECOVERY FULL 
GO
ALTER DATABASE [QLNThuoc] SET  MULTI_USER 
GO
ALTER DATABASE [QLNThuoc] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [QLNThuoc] SET DB_CHAINING OFF 
GO
ALTER DATABASE [QLNThuoc] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [QLNThuoc] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [QLNThuoc] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [QLNThuoc] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'QLNThuoc', N'ON'
GO
ALTER DATABASE [QLNThuoc] SET QUERY_STORE = ON
GO
ALTER DATABASE [QLNThuoc] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [QLNThuoc]
GO
/****** Object:  Table [dbo].[CTHoaDon]    Script Date: 4/24/2025 2:09:03 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CTHoaDon](
	[maHD] [nvarchar](10) NOT NULL,
	[maThuoc] [nvarchar](10) NOT NULL,
	[soLuong] [int] NOT NULL,
	[donGia] [float] NOT NULL,
 CONSTRAINT [PK_CTHoaDon] PRIMARY KEY CLUSTERED 
(
	[maHD] ASC,
	[maThuoc] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CTPhieuDoi]    Script Date: 4/24/2025 2:09:03 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CTPhieuDoi](
	[maPD] [nvarchar](10) NOT NULL,
	[maThuocCu] [nvarchar](10) NOT NULL,
	[soLuongCu] [int] NOT NULL,
	[donGiaCu] [float] NOT NULL,
	[maThuocMoi] [nvarchar](10) NOT NULL,
	[soLuongMoi] [int] NOT NULL,
	[donGiaMoi] [float] NOT NULL,
	[tongTien] [float] NOT NULL,
 CONSTRAINT [PK_CTPhieuDoiTra] PRIMARY KEY CLUSTERED 
(
	[maPD] ASC,
	[maThuocCu] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CTPhieuNhap]    Script Date: 4/24/2025 2:09:03 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CTPhieuNhap](
	[maPN] [nvarchar](50) NOT NULL,
	[maThuoc] [nvarchar](10) NOT NULL,
	[soLuong] [int] NOT NULL,
	[donGia] [float] NOT NULL,
 CONSTRAINT [PK_CTPhieuNhap] PRIMARY KEY CLUSTERED 
(
	[maPN] ASC,
	[maThuoc] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CTPhieuTra]    Script Date: 4/24/2025 2:09:03 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CTPhieuTra](
	[maPT] [nvarchar](10) NOT NULL,
	[maThuoc] [nvarchar](10) NOT NULL,
	[soLuong] [int] NOT NULL,
	[donGia] [float] NOT NULL,
 CONSTRAINT [PK_CTPhieuTra] PRIMARY KEY CLUSTERED 
(
	[maPT] ASC,
	[maThuoc] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DanhMuc]    Script Date: 4/24/2025 2:09:03 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DanhMuc](
	[maDM] [nvarchar](10) NOT NULL,
	[tenDM] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_DanhMuc_1] PRIMARY KEY CLUSTERED 
(
	[maDM] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DonViTinh]    Script Date: 4/24/2025 2:09:03 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DonViTinh](
	[maDVT] [nvarchar](10) NOT NULL,
	[tenDVT] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_DonViTinh] PRIMARY KEY CLUSTERED 
(
	[maDVT] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[HoaDon]    Script Date: 4/24/2025 2:09:03 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HoaDon](
	[maHD] [nvarchar](10) NOT NULL,
	[maNV] [nvarchar](10) NOT NULL,
	[maKH] [nvarchar](10) NOT NULL,
	[thoiGian] [datetime] NOT NULL,
 CONSTRAINT [PK_HoaDon] PRIMARY KEY CLUSTERED 
(
	[maHD] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[KhachHang]    Script Date: 4/24/2025 2:09:03 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[KhachHang](
	[maKH] [nvarchar](10) NOT NULL,
	[tenKH] [nvarchar](50) NOT NULL,
	[gioiTinh] [nvarchar](10) NOT NULL,
	[SDT] [nvarchar](10) NOT NULL,
	[tuoi] [int] NOT NULL,
 CONSTRAINT [PK_KhachHang] PRIMARY KEY CLUSTERED 
(
	[maKH] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NhaCungCap]    Script Date: 4/24/2025 2:09:03 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NhaCungCap](
	[maNCC] [nvarchar](10) NOT NULL,
	[tenNCC] [nvarchar](50) NOT NULL,
	[diaChiNCC] [nvarchar](50) NOT NULL,
	[SĐT] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_NhaCungCap] PRIMARY KEY CLUSTERED 
(
	[maNCC] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[NhanVien]    Script Date: 4/24/2025 2:09:03 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[NhanVien](
	[maNV] [nvarchar](10) NOT NULL,
	[hoTen] [nvarchar](50) NOT NULL,
	[sdt] [varchar](12) NOT NULL,
	[gioiTinh] [nvarchar](50) NOT NULL,
	[dtSinh] [date] NOT NULL,
	[ngayVaoLam] [date] NOT NULL,
	[chucVu] [nvarchar](50) NOT NULL,
	[CCCD] [nvarchar](12) NOT NULL,
 CONSTRAINT [PK_NhanVien] PRIMARY KEY CLUSTERED 
(
	[maNV] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PhieuDoi]    Script Date: 4/24/2025 2:09:03 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PhieuDoi](
	[maPD] [nvarchar](10) NOT NULL,
	[maNV] [nvarchar](10) NOT NULL,
	[maKH] [nvarchar](10) NOT NULL,
	[maHD] [nvarchar](10) NOT NULL,
	[thoiGian] [date] NOT NULL,
	[lyDo] [nvarchar](150) NULL,
 CONSTRAINT [PK_PhieuDoiTra] PRIMARY KEY CLUSTERED 
(
	[maPD] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PhieuNhap]    Script Date: 4/24/2025 2:09:03 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PhieuNhap](
	[maPN] [nvarchar](50) NOT NULL,
	[maNV] [nvarchar](10) NOT NULL,
	[maNCC] [nvarchar](10) NOT NULL,
	[thoiGian] [datetime] NOT NULL,
 CONSTRAINT [PK_PhieuNhap] PRIMARY KEY CLUSTERED 
(
	[maPN] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PhieuTra]    Script Date: 4/24/2025 2:09:03 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PhieuTra](
	[maPT] [nvarchar](10) NOT NULL,
	[maNV] [nvarchar](10) NULL,
	[maKH] [nvarchar](10) NULL,
	[maHD] [nvarchar](10) NULL,
	[thoiGian] [date] NULL,
	[lyDo] [nvarchar](150) NULL,
 CONSTRAINT [PK_PhieuTra] PRIMARY KEY CLUSTERED 
(
	[maPT] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TaiKhoan]    Script Date: 4/24/2025 2:09:03 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TaiKhoan](
	[maTK] [nvarchar](50) NOT NULL,
	[UserName] [nvarchar](100) NOT NULL,
	[Password] [nvarchar](100) NOT NULL,
	[maNV] [nvarchar](10) NULL,
 CONSTRAINT [PK__TaiKhoan__7A22625E9B882179] PRIMARY KEY CLUSTERED 
(
	[maTK] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Thuoc]    Script Date: 4/24/2025 2:09:03 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Thuoc](
	[maThuoc] [nvarchar](10) NOT NULL,
	[tenThuoc] [nvarchar](50) NOT NULL,
	[anh] [varbinary](max) NULL,
	[thanhPhanThuoc] [nvarchar](50) NULL,
	[maDM] [nvarchar](10) NOT NULL,
	[maDVT] [nvarchar](10) NOT NULL,
	[maXX] [nvarchar](10) NOT NULL,
	[soLuong] [int] NOT NULL,
	[giaNhap] [float] NOT NULL,
	[donGia] [float] NOT NULL,
	[HSD] [datetime] NOT NULL,
 CONSTRAINT [PK_Thuoc_1] PRIMARY KEY CLUSTERED 
(
	[maThuoc] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[XuatXu]    Script Date: 4/24/2025 2:09:03 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[XuatXu](
	[maXX] [nvarchar](10) NOT NULL,
	[tenXX] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_XuatXu] PRIMARY KEY CLUSTERED 
(
	[maXX] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-001', N'T-001', 10, 1000)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-001', N'T-002', 15, 2000)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-002', N'T-003', 8, 800)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-002', N'T-004', 10, 1500)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-003', N'T-005', 12, 500)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-003', N'T-006', 10, 2500)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-004', N'T-007', 7, 2200)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-004', N'T-008', 10, 1200)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-005', N'T-009', 5, 6500)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-005', N'T-010', 8, 900)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-006', N'T-011', 6, 1300)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-006', N'T-012', 8, 800)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-007', N'T-013', 10, 15000)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-007', N'T-014', 4, 2700)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-008', N'T-015', 15, 1200)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-008', N'T-016', 20, 1000)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-009', N'T-017', 8, 1500)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-009', N'T-018', 10, 500)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-010', N'T-019', 6, 2000)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-010', N'T-020', 7, 1800)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-011', N'T-021', 5, 3500)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-011', N'T-022', 8, 1500)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-012', N'T-023', 12, 900)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-012', N'T-024', 4, 1300)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-013', N'T-025', 10, 5000)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-013', N'T-026', 15, 1100)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-014', N'T-027', 20, 1800)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-014', N'T-028', 8, 1500)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-015', N'T-029', 6, 4000)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-015', N'T-030', 10, 1300)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-016', N'T-031', 12, 3000)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-016', N'T-032', 8, 2500)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-017', N'T-033', 10, 4000)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-017', N'T-034', 7, 1600)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-018', N'T-035', 15, 2200)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-018', N'T-036', 8, 2700)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-019', N'T-037', 10, 2000)
INSERT [dbo].[CTHoaDon] ([maHD], [maThuoc], [soLuong], [donGia]) VALUES (N'HD-019', N'T-038', 12, 6000)
GO
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-001', N'T-001', 100, 500)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-001', N'T-002', 200, 1000)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-001', N'T-003', 150, 300)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-002', N'T-004', 120, 700)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-002', N'T-005', 250, 200)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-002', N'T-006', 80, 1500)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-003', N'T-007', 90, 1200)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-003', N'T-008', 100, 600)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-003', N'T-009', 60, 4000)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-004', N'T-010', 300, 500)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-004', N'T-011', 100, 700)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-004', N'T-012', 120, 400)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-005', N'T-013', 50, 10000)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-005', N'T-014', 60, 1800)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-005', N'T-015', 250, 700)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-006', N'T-016', 140, 600)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-006', N'T-017', 100, 800)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-006', N'T-018', 300, 300)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-007', N'T-019', 90, 1200)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-007', N'T-020', 150, 1000)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-007', N'T-021', 100, 2000)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-008', N'T-022', 200, 900)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-008', N'T-023', 150, 500)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-008', N'T-024', 90, 700)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-009', N'T-025', 60, 3000)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-009', N'T-026', 180, 600)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-009', N'T-027', 110, 1000)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-010', N'T-028', 130, 800)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-010', N'T-029', 75, 2500)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-010', N'T-030', 100, 700)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-011', N'T-031', 50, 2000)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-011', N'T-032', 90, 1600)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-011', N'T-033', 100, 2500)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-012', N'T-034', 120, 1000)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-012', N'T-035', 80, 1400)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-012', N'T-036', 90, 1800)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-013', N'T-037', 100, 1200)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-013', N'T-038', 110, 3500)
INSERT [dbo].[CTPhieuNhap] ([maPN], [maThuoc], [soLuong], [donGia]) VALUES (N'PN-014', N'T-039', 130, 900)
GO
INSERT [dbo].[DanhMuc] ([maDM], [tenDM]) VALUES (N'DM-001', N'Thuốc cảm cúm')
INSERT [dbo].[DanhMuc] ([maDM], [tenDM]) VALUES (N'DM-002', N'Thuốc đau đầu')
INSERT [dbo].[DanhMuc] ([maDM], [tenDM]) VALUES (N'DM-003', N'Thuốc kháng sinh')
INSERT [dbo].[DanhMuc] ([maDM], [tenDM]) VALUES (N'DM-004', N'Thuốc bổ')
INSERT [dbo].[DanhMuc] ([maDM], [tenDM]) VALUES (N'DM-005', N'Thuốc dạ dày')
INSERT [dbo].[DanhMuc] ([maDM], [tenDM]) VALUES (N'DM-006', N'Thuốc huyết áp')
INSERT [dbo].[DanhMuc] ([maDM], [tenDM]) VALUES (N'DM-007', N'Thuốc tim mạch')
INSERT [dbo].[DanhMuc] ([maDM], [tenDM]) VALUES (N'DM-008', N'Thuốc giảm đau')
INSERT [dbo].[DanhMuc] ([maDM], [tenDM]) VALUES (N'DM-009', N'Thuốc dị ứng')
INSERT [dbo].[DanhMuc] ([maDM], [tenDM]) VALUES (N'DM-010', N'Thuốc ho')
GO
INSERT [dbo].[DonViTinh] ([maDVT], [tenDVT]) VALUES (N'DVT-001', N'Viên')
INSERT [dbo].[DonViTinh] ([maDVT], [tenDVT]) VALUES (N'DVT-002', N'Hộp')
INSERT [dbo].[DonViTinh] ([maDVT], [tenDVT]) VALUES (N'DVT-003', N'Chai')
INSERT [dbo].[DonViTinh] ([maDVT], [tenDVT]) VALUES (N'DVT-004', N'Tuýp')
INSERT [dbo].[DonViTinh] ([maDVT], [tenDVT]) VALUES (N'DVT-005', N'Gói')
INSERT [dbo].[DonViTinh] ([maDVT], [tenDVT]) VALUES (N'DVT-006', N'Ống')
INSERT [dbo].[DonViTinh] ([maDVT], [tenDVT]) VALUES (N'DVT-007', N'Vỉ')
INSERT [dbo].[DonViTinh] ([maDVT], [tenDVT]) VALUES (N'DVT-008', N'Lọ')
INSERT [dbo].[DonViTinh] ([maDVT], [tenDVT]) VALUES (N'DVT-009', N'Tray')
INSERT [dbo].[DonViTinh] ([maDVT], [tenDVT]) VALUES (N'DVT-010', N'Bịch')
GO
INSERT [dbo].[HoaDon] ([maHD], [maNV], [maKH], [thoiGian]) VALUES (N'HD-001', N'NV-001', N'KH-001', CAST(N'2025-04-12T02:57:00.000' AS DateTime))
INSERT [dbo].[HoaDon] ([maHD], [maNV], [maKH], [thoiGian]) VALUES (N'HD-002', N'NV-002', N'KH-002', CAST(N'2025-04-12T02:58:00.000' AS DateTime))
INSERT [dbo].[HoaDon] ([maHD], [maNV], [maKH], [thoiGian]) VALUES (N'HD-003', N'NV-003', N'KH-003', CAST(N'2025-04-12T02:58:00.000' AS DateTime))
INSERT [dbo].[HoaDon] ([maHD], [maNV], [maKH], [thoiGian]) VALUES (N'HD-004', N'NV-004', N'KH-004', CAST(N'2025-04-12T02:58:00.000' AS DateTime))
INSERT [dbo].[HoaDon] ([maHD], [maNV], [maKH], [thoiGian]) VALUES (N'HD-005', N'NV-005', N'KH-005', CAST(N'2025-04-12T02:59:00.000' AS DateTime))
INSERT [dbo].[HoaDon] ([maHD], [maNV], [maKH], [thoiGian]) VALUES (N'HD-006', N'NV-006', N'KH-006', CAST(N'2025-04-12T03:00:00.000' AS DateTime))
INSERT [dbo].[HoaDon] ([maHD], [maNV], [maKH], [thoiGian]) VALUES (N'HD-007', N'NV-007', N'KH-007', CAST(N'2025-04-12T03:01:00.000' AS DateTime))
INSERT [dbo].[HoaDon] ([maHD], [maNV], [maKH], [thoiGian]) VALUES (N'HD-008', N'NV-008', N'KH-008', CAST(N'2025-04-12T03:02:00.000' AS DateTime))
INSERT [dbo].[HoaDon] ([maHD], [maNV], [maKH], [thoiGian]) VALUES (N'HD-009', N'NV-009', N'KH-009', CAST(N'2025-04-12T03:03:00.000' AS DateTime))
INSERT [dbo].[HoaDon] ([maHD], [maNV], [maKH], [thoiGian]) VALUES (N'HD-010', N'NV-010', N'KH-010', CAST(N'2025-04-12T03:04:00.000' AS DateTime))
INSERT [dbo].[HoaDon] ([maHD], [maNV], [maKH], [thoiGian]) VALUES (N'HD-011', N'NV-001', N'KH-001', CAST(N'2025-04-12T03:05:00.000' AS DateTime))
INSERT [dbo].[HoaDon] ([maHD], [maNV], [maKH], [thoiGian]) VALUES (N'HD-012', N'NV-002', N'KH-002', CAST(N'2025-04-12T03:06:00.000' AS DateTime))
INSERT [dbo].[HoaDon] ([maHD], [maNV], [maKH], [thoiGian]) VALUES (N'HD-013', N'NV-003', N'KH-003', CAST(N'2025-04-12T03:07:00.000' AS DateTime))
INSERT [dbo].[HoaDon] ([maHD], [maNV], [maKH], [thoiGian]) VALUES (N'HD-014', N'NV-004', N'KH-004', CAST(N'2025-04-12T03:08:00.000' AS DateTime))
INSERT [dbo].[HoaDon] ([maHD], [maNV], [maKH], [thoiGian]) VALUES (N'HD-015', N'NV-005', N'KH-005', CAST(N'2025-04-12T03:09:00.000' AS DateTime))
INSERT [dbo].[HoaDon] ([maHD], [maNV], [maKH], [thoiGian]) VALUES (N'HD-016', N'NV-006', N'KH-006', CAST(N'2025-04-12T03:10:00.000' AS DateTime))
INSERT [dbo].[HoaDon] ([maHD], [maNV], [maKH], [thoiGian]) VALUES (N'HD-017', N'NV-007', N'KH-007', CAST(N'2025-04-12T03:11:00.000' AS DateTime))
INSERT [dbo].[HoaDon] ([maHD], [maNV], [maKH], [thoiGian]) VALUES (N'HD-018', N'NV-008', N'KH-008', CAST(N'2025-04-12T03:12:00.000' AS DateTime))
INSERT [dbo].[HoaDon] ([maHD], [maNV], [maKH], [thoiGian]) VALUES (N'HD-019', N'NV-009', N'KH-009', CAST(N'2025-04-12T03:13:00.000' AS DateTime))
GO
INSERT [dbo].[KhachHang] ([maKH], [tenKH], [gioiTinh], [SDT], [tuoi]) VALUES (N'KH-001', N'Nguyễn Văn Bảo', N'Nam', N'0901000001', 19)
INSERT [dbo].[KhachHang] ([maKH], [tenKH], [gioiTinh], [SDT], [tuoi]) VALUES (N'KH-002', N'Lê Thị Bình', N'Nữ', N'0901000002', 20)
INSERT [dbo].[KhachHang] ([maKH], [tenKH], [gioiTinh], [SDT], [tuoi]) VALUES (N'KH-003', N'Trần Văn Sỹ', N'Nam', N'0901000003', 23)
INSERT [dbo].[KhachHang] ([maKH], [tenKH], [gioiTinh], [SDT], [tuoi]) VALUES (N'KH-004', N'Phạm Thị Hạnh', N'Nữ', N'0901000004', 17)
INSERT [dbo].[KhachHang] ([maKH], [tenKH], [gioiTinh], [SDT], [tuoi]) VALUES (N'KH-005', N'Hoàng Văn Tuấn Anh', N'Nam', N'0901000005', 30)
INSERT [dbo].[KhachHang] ([maKH], [tenKH], [gioiTinh], [SDT], [tuoi]) VALUES (N'KH-006', N'Đỗ Thị Tuyết', N'Nữ', N'0901000006', 27)
INSERT [dbo].[KhachHang] ([maKH], [tenKH], [gioiTinh], [SDT], [tuoi]) VALUES (N'KH-007', N'Ngô Văn Bảo', N'Nam', N'0901000007', 40)
INSERT [dbo].[KhachHang] ([maKH], [tenKH], [gioiTinh], [SDT], [tuoi]) VALUES (N'KH-008', N'Vũ Thị Hảo', N'Nữ', N'0901000008', 60)
INSERT [dbo].[KhachHang] ([maKH], [tenKH], [gioiTinh], [SDT], [tuoi]) VALUES (N'KH-009', N'Phạm Văn Huy', N'Nam', N'0901000009', 35)
INSERT [dbo].[KhachHang] ([maKH], [tenKH], [gioiTinh], [SDT], [tuoi]) VALUES (N'KH-010', N'Tạ Thị Loan', N'Nữ', N'0901000010', 32)
INSERT [dbo].[KhachHang] ([maKH], [tenKH], [gioiTinh], [SDT], [tuoi]) VALUES (N'KH-011', N'Trần Sỹ Hạo', N'Nam', N'0901000011', 18)
INSERT [dbo].[KhachHang] ([maKH], [tenKH], [gioiTinh], [SDT], [tuoi]) VALUES (N'KH-012', N'Đinh Ngọc Dĩ Hào', N'Nam', N'0901000012', 21)
GO
INSERT [dbo].[NhaCungCap] ([maNCC], [tenNCC], [diaChiNCC], [SĐT]) VALUES (N'NCC-001', N'Công ty CP Dược Hậu Giang', N'Cần Thơ', N'0281000001')
INSERT [dbo].[NhaCungCap] ([maNCC], [tenNCC], [diaChiNCC], [SĐT]) VALUES (N'NCC-002', N'Công ty Dược phẩm TraPhaco', N'Hà Nội', N'0281000002')
INSERT [dbo].[NhaCungCap] ([maNCC], [tenNCC], [diaChiNCC], [SĐT]) VALUES (N'NCC-003', N'Công ty TNHH Sanofi - Avetis Việt Nam', N'TP.HCM', N'0281000003')
INSERT [dbo].[NhaCungCap] ([maNCC], [tenNCC], [diaChiNCC], [SĐT]) VALUES (N'NCC-004', N'Công ty Sản xuất Dược phẩm Imexpharm', N'Đồng Tháp', N'0281000004')
INSERT [dbo].[NhaCungCap] ([maNCC], [tenNCC], [diaChiNCC], [SĐT]) VALUES (N'NCC-005', N'Công ty CP Dược - Trang thiết bị y tế Bình Định', N'Bình Định', N'0281000005')
INSERT [dbo].[NhaCungCap] ([maNCC], [tenNCC], [diaChiNCC], [SĐT]) VALUES (N'NCC-006', N'Công ty cổ phần Pymepharco', N'Phú Yên', N'0281000006')
INSERT [dbo].[NhaCungCap] ([maNCC], [tenNCC], [diaChiNCC], [SĐT]) VALUES (N'NCC-007', N'Công ty xuất nhập khẩu y tế Domesco ', N'Đồng Tháp', N'0281000007')
INSERT [dbo].[NhaCungCap] ([maNCC], [tenNCC], [diaChiNCC], [SĐT]) VALUES (N'NCC-008', N'Công ty Cổ phần Dược phẩm TV.Pharm', N'Trà Vinh', N'0281000008')
INSERT [dbo].[NhaCungCap] ([maNCC], [tenNCC], [diaChiNCC], [SĐT]) VALUES (N'NCC-009', N'Công ty CP Dược phẩm Hà Tây', N'Hà Nội', N'0281000009')
GO
INSERT [dbo].[NhanVien] ([maNV], [hoTen], [sdt], [gioiTinh], [dtSinh], [ngayVaoLam], [chucVu], [CCCD]) VALUES (N'NV-001', N'Đinh Ngọc Dĩ Hào', N'0909000001', N'Nam', CAST(N'2004-02-11' AS Date), CAST(N'2025-12-04' AS Date), N'Quản lý', N'070204000001')
INSERT [dbo].[NhanVien] ([maNV], [hoTen], [sdt], [gioiTinh], [dtSinh], [ngayVaoLam], [chucVu], [CCCD]) VALUES (N'NV-002', N'Trần Huỳnh Sỹ Đạt', N'0909000002', N'Nam', CAST(N'2004-02-22' AS Date), CAST(N'2025-12-04' AS Date), N'Quản lý', N'020304000002')
INSERT [dbo].[NhanVien] ([maNV], [hoTen], [sdt], [gioiTinh], [dtSinh], [ngayVaoLam], [chucVu], [CCCD]) VALUES (N'NV-003', N'Phan Nhật Đăng', N'0909000003', N'Nam', CAST(N'2004-03-11' AS Date), CAST(N'2025-12-04' AS Date), N'Quản lý', N'070204000003')
INSERT [dbo].[NhanVien] ([maNV], [hoTen], [sdt], [gioiTinh], [dtSinh], [ngayVaoLam], [chucVu], [CCCD]) VALUES (N'NV-004', N'Vũ Tiến Đạt', N'0909000004', N'Nam', CAST(N'2004-02-01' AS Date), CAST(N'2025-04-12' AS Date), N'Nhân Viên', N'070204000004')
INSERT [dbo].[NhanVien] ([maNV], [hoTen], [sdt], [gioiTinh], [dtSinh], [ngayVaoLam], [chucVu], [CCCD]) VALUES (N'NV-005', N'Lê Thị Tuyết Ly', N'0909000005', N'Nữ', CAST(N'2004-02-11' AS Date), CAST(N'2025-12-04' AS Date), N'Nhân Viên', N'070204000005')
INSERT [dbo].[NhanVien] ([maNV], [hoTen], [sdt], [gioiTinh], [dtSinh], [ngayVaoLam], [chucVu], [CCCD]) VALUES (N'NV-006', N'Trần Thị Thúy Hằng', N'0909000006', N'Nữ', CAST(N'2004-08-03' AS Date), CAST(N'2025-12-04' AS Date), N'Nhân Viên', N'070204000006')
INSERT [dbo].[NhanVien] ([maNV], [hoTen], [sdt], [gioiTinh], [dtSinh], [ngayVaoLam], [chucVu], [CCCD]) VALUES (N'NV-007', N'Trần Bảo Nhi', N'0909000007', N'Nữ', CAST(N'2004-12-02' AS Date), CAST(N'2025-04-12' AS Date), N'Nhân Viên', N'070204000007')
INSERT [dbo].[NhanVien] ([maNV], [hoTen], [sdt], [gioiTinh], [dtSinh], [ngayVaoLam], [chucVu], [CCCD]) VALUES (N'NV-008', N'Đinh Ngọc Hồng Ân', N'0909000008', N'Nữ', CAST(N'2004-03-09' AS Date), CAST(N'2025-12-04' AS Date), N'Nhân Viên', N'070204000008')
INSERT [dbo].[NhanVien] ([maNV], [hoTen], [sdt], [gioiTinh], [dtSinh], [ngayVaoLam], [chucVu], [CCCD]) VALUES (N'NV-009', N'Nguyễn Thị Kim Liên', N'0909090909', N'Nữ', CAST(N'2004-04-30' AS Date), CAST(N'2025-12-04' AS Date), N'Nhân Viên', N'070204000009')
INSERT [dbo].[NhanVien] ([maNV], [hoTen], [sdt], [gioiTinh], [dtSinh], [ngayVaoLam], [chucVu], [CCCD]) VALUES (N'NV-010', N'Vũ Thế Phong', N'0909000010', N'Nam', CAST(N'2004-03-12' AS Date), CAST(N'2025-12-04' AS Date), N'Nhân viên', N'070204000010')
INSERT [dbo].[NhanVien] ([maNV], [hoTen], [sdt], [gioiTinh], [dtSinh], [ngayVaoLam], [chucVu], [CCCD]) VALUES (N'NV-011', N'Lê Viết Hải', N'0909000011', N'Nam', CAST(N'2005-04-15' AS Date), CAST(N'2025-04-17' AS Date), N'Quản lý', N'070204000011')
INSERT [dbo].[NhanVien] ([maNV], [hoTen], [sdt], [gioiTinh], [dtSinh], [ngayVaoLam], [chucVu], [CCCD]) VALUES (N'NV-012', N'Lê Viết Hải', N'1', N'Nam', CAST(N'2025-04-09' AS Date), CAST(N'2025-04-17' AS Date), N'Nhân viên', N'1')
INSERT [dbo].[NhanVien] ([maNV], [hoTen], [sdt], [gioiTinh], [dtSinh], [ngayVaoLam], [chucVu], [CCCD]) VALUES (N'NV-013', N'1', N'1', N'Nam', CAST(N'2025-04-17' AS Date), CAST(N'2025-04-17' AS Date), N'Nhân viên', N'1')
INSERT [dbo].[NhanVien] ([maNV], [hoTen], [sdt], [gioiTinh], [dtSinh], [ngayVaoLam], [chucVu], [CCCD]) VALUES (N'NV-014', N'Nguyễn Văn Sỹ', N'1', N'Nam', CAST(N'2004-04-23' AS Date), CAST(N'2025-04-21' AS Date), N'Nhân viên', N'1')
GO
INSERT [dbo].[PhieuDoi] ([maPD], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PD-001', N'NV-001', N'KH-001', N'HD-001', CAST(N'2025-04-10' AS Date), N'Lỗi sản phẩm')
INSERT [dbo].[PhieuDoi] ([maPD], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PD-002', N'NV-002', N'KH-002', N'HD-002', CAST(N'2025-04-11' AS Date), N'Sai mặt hàng')
INSERT [dbo].[PhieuDoi] ([maPD], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PD-003', N'NV-003', N'KH-003', N'HD-003', CAST(N'2025-04-11' AS Date), N'Hết hạn sử dụng')
INSERT [dbo].[PhieuDoi] ([maPD], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PD-004', N'NV-004', N'KH-004', N'HD-004', CAST(N'2025-04-11' AS Date), N'Không đúng đơn đặt')
INSERT [dbo].[PhieuDoi] ([maPD], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PD-005', N'NV-005', N'KH-005', N'HD-005', CAST(N'2025-04-12' AS Date), N'Khách không hài lòng')
INSERT [dbo].[PhieuDoi] ([maPD], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PD-006', N'NV-006', N'KH-006', N'HD-006', CAST(N'2025-04-12' AS Date), N'Bị vỡ sản phẩm')
INSERT [dbo].[PhieuDoi] ([maPD], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PD-007', N'NV-007', N'KH-007', N'HD-007', CAST(N'2025-04-12' AS Date), N'Không đúng mẫu')
INSERT [dbo].[PhieuDoi] ([maPD], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PD-008', N'NV-008', N'KH-008', N'HD-008', CAST(N'2025-04-12' AS Date), N'Lỗi kỹ thuật')
INSERT [dbo].[PhieuDoi] ([maPD], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PD-009', N'NV-009', N'KH-009', N'HD-009', CAST(N'2025-04-12' AS Date), N'Giao thiếu sản phẩm')
INSERT [dbo].[PhieuDoi] ([maPD], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PD-010', N'NV-010', N'KH-010', N'HD-010', CAST(N'2025-04-12' AS Date), N'Khác với mô tả')
INSERT [dbo].[PhieuDoi] ([maPD], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PD-011', N'NV-001', N'KH-001', N'HD-011', CAST(N'2025-04-12' AS Date), N'Khách yêu cầu đổi')
INSERT [dbo].[PhieuDoi] ([maPD], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PD-012', N'NV-002', N'KH-002', N'HD-012', CAST(N'2025-04-12' AS Date), N'Không còn nhu cầu')
INSERT [dbo].[PhieuDoi] ([maPD], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PD-013', N'NV-003', N'KH-003', N'HD-013', CAST(N'2025-04-12' AS Date), N'Đơn hàng sai')
INSERT [dbo].[PhieuDoi] ([maPD], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PD-014', N'NV-004', N'KH-004', N'HD-014', CAST(N'2025-04-12' AS Date), N'Lỗi bao bì')
INSERT [dbo].[PhieuDoi] ([maPD], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PD-015', N'NV-005', N'KH-005', N'HD-015', CAST(N'2025-04-12' AS Date), N'Bị trầy xước')
GO
INSERT [dbo].[PhieuNhap] ([maPN], [maNV], [maNCC], [thoiGian]) VALUES (N'PN-001', N'NV-001', N'NCC-001', CAST(N'2025-04-12T09:00:00.000' AS DateTime))
INSERT [dbo].[PhieuNhap] ([maPN], [maNV], [maNCC], [thoiGian]) VALUES (N'PN-002', N'NV-002', N'NCC-002', CAST(N'2025-04-12T09:10:00.000' AS DateTime))
INSERT [dbo].[PhieuNhap] ([maPN], [maNV], [maNCC], [thoiGian]) VALUES (N'PN-003', N'NV-003', N'NCC-003', CAST(N'2025-04-12T09:20:00.000' AS DateTime))
INSERT [dbo].[PhieuNhap] ([maPN], [maNV], [maNCC], [thoiGian]) VALUES (N'PN-004', N'NV-004', N'NCC-004', CAST(N'2025-04-12T09:30:00.000' AS DateTime))
INSERT [dbo].[PhieuNhap] ([maPN], [maNV], [maNCC], [thoiGian]) VALUES (N'PN-005', N'NV-005', N'NCC-005', CAST(N'2025-04-12T09:40:00.000' AS DateTime))
INSERT [dbo].[PhieuNhap] ([maPN], [maNV], [maNCC], [thoiGian]) VALUES (N'PN-006', N'NV-006', N'NCC-006', CAST(N'2025-04-12T09:50:00.000' AS DateTime))
INSERT [dbo].[PhieuNhap] ([maPN], [maNV], [maNCC], [thoiGian]) VALUES (N'PN-007', N'NV-007', N'NCC-007', CAST(N'2025-04-12T10:00:00.000' AS DateTime))
INSERT [dbo].[PhieuNhap] ([maPN], [maNV], [maNCC], [thoiGian]) VALUES (N'PN-008', N'NV-008', N'NCC-008', CAST(N'2025-04-12T10:10:00.000' AS DateTime))
INSERT [dbo].[PhieuNhap] ([maPN], [maNV], [maNCC], [thoiGian]) VALUES (N'PN-009', N'NV-009', N'NCC-009', CAST(N'2025-04-12T10:20:00.000' AS DateTime))
INSERT [dbo].[PhieuNhap] ([maPN], [maNV], [maNCC], [thoiGian]) VALUES (N'PN-010', N'NV-010', N'NCC-001', CAST(N'2025-04-12T10:30:00.000' AS DateTime))
INSERT [dbo].[PhieuNhap] ([maPN], [maNV], [maNCC], [thoiGian]) VALUES (N'PN-011', N'NV-001', N'NCC-002', CAST(N'2025-04-12T10:40:00.000' AS DateTime))
INSERT [dbo].[PhieuNhap] ([maPN], [maNV], [maNCC], [thoiGian]) VALUES (N'PN-012', N'NV-002', N'NCC-003', CAST(N'2025-04-12T10:50:00.000' AS DateTime))
INSERT [dbo].[PhieuNhap] ([maPN], [maNV], [maNCC], [thoiGian]) VALUES (N'PN-013', N'NV-003', N'NCC-004', CAST(N'2025-04-12T11:00:00.000' AS DateTime))
INSERT [dbo].[PhieuNhap] ([maPN], [maNV], [maNCC], [thoiGian]) VALUES (N'PN-014', N'NV-004', N'NCC-005', CAST(N'2025-04-12T11:10:00.000' AS DateTime))
GO
INSERT [dbo].[PhieuTra] ([maPT], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PT-001', N'NV-001', N'KH-001', N'HD-001', CAST(N'2025-04-10' AS Date), N'Lỗi sản phẩm')
INSERT [dbo].[PhieuTra] ([maPT], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PT-002', N'NV-002', N'KH-002', N'HD-002', CAST(N'2025-04-10' AS Date), N'Sai mặt hàng')
INSERT [dbo].[PhieuTra] ([maPT], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PT-003', N'NV-003', N'KH-003', N'HD-003', CAST(N'2025-04-11' AS Date), N'Hết hạn sử dụng')
INSERT [dbo].[PhieuTra] ([maPT], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PT-004', N'NV-004', N'KH-004', N'HD-004', CAST(N'2025-04-11' AS Date), N'Không đúng đơn đặt')
INSERT [dbo].[PhieuTra] ([maPT], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PT-005', N'NV-001', N'KH-002', N'HD-005', CAST(N'2025-04-11' AS Date), N'Không hài lòng')
INSERT [dbo].[PhieuTra] ([maPT], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PT-006', N'NV-010', N'KH-003', N'HD-006', CAST(N'2025-04-12' AS Date), N'Bị vỡ sản phẩm')
INSERT [dbo].[PhieuTra] ([maPT], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PT-007', N'NV-009', N'KH-004', N'HD-007', CAST(N'2025-04-12' AS Date), N'Không đúng mẫu')
INSERT [dbo].[PhieuTra] ([maPT], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PT-008', N'NV-008', N'KH-001', N'HD-008', CAST(N'2025-04-12' AS Date), N'Lỗi kỹ thuật')
INSERT [dbo].[PhieuTra] ([maPT], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PT-009', N'NV-007', N'KH-003', N'HD-009', CAST(N'2025-04-12' AS Date), N'Giao thiếu sản phẩm')
INSERT [dbo].[PhieuTra] ([maPT], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PT-011', N'NV-005', N'KH-001', N'HD-011', CAST(N'2025-04-12' AS Date), N'Khách muốn trả')
INSERT [dbo].[PhieuTra] ([maPT], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PT-012', N'NV-004', N'KH-002', N'HD-012', CAST(N'2025-04-12' AS Date), N'Không còn nhu cầu')
INSERT [dbo].[PhieuTra] ([maPT], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PT-013', N'NV-003', N'KH-004', N'HD-013', CAST(N'2025-04-12' AS Date), N'Đơn hàng sai')
INSERT [dbo].[PhieuTra] ([maPT], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PT-014', N'NV-002', N'KH-001', N'HD-014', CAST(N'2025-04-12' AS Date), N'Lỗi bao bì')
INSERT [dbo].[PhieuTra] ([maPT], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PT-015', N'NV-001', N'KH-002', N'HD-015', CAST(N'2025-04-12' AS Date), N'Bị trầy xước')
INSERT [dbo].[PhieuTra] ([maPT], [maNV], [maKH], [maHD], [thoiGian], [lyDo]) VALUES (N'PT-100', N'NV-006', N'KH-004', N'HD-010', CAST(N'2025-04-12' AS Date), N'Không đúng mô tả')
GO
INSERT [dbo].[TaiKhoan] ([maTK], [UserName], [Password], [maNV]) VALUES (N'TK-001', N'DiHao', N'12345678', N'NV-001')
INSERT [dbo].[TaiKhoan] ([maTK], [UserName], [Password], [maNV]) VALUES (N'TK-002', N'SyDat', N'12345678', N'NV-002')
INSERT [dbo].[TaiKhoan] ([maTK], [UserName], [Password], [maNV]) VALUES (N'TK-003', N'DangPhan', N'12345678', N'NV-003')
INSERT [dbo].[TaiKhoan] ([maTK], [UserName], [Password], [maNV]) VALUES (N'TK-004', N'TienDat', N'12345678', N'NV-004')
INSERT [dbo].[TaiKhoan] ([maTK], [UserName], [Password], [maNV]) VALUES (N'TK-005', N'TuyetLy', N'12345678', N'NV-005')
INSERT [dbo].[TaiKhoan] ([maTK], [UserName], [Password], [maNV]) VALUES (N'TK-006', N'ThuyHang', N'12345678', N'NV-006')
INSERT [dbo].[TaiKhoan] ([maTK], [UserName], [Password], [maNV]) VALUES (N'TK-007', N'BaoNhi', N'12345678', N'NV-007')
INSERT [dbo].[TaiKhoan] ([maTK], [UserName], [Password], [maNV]) VALUES (N'TK-008', N'HongAn', N'12345678', N'NV-008')
INSERT [dbo].[TaiKhoan] ([maTK], [UserName], [Password], [maNV]) VALUES (N'TK-009', N'KimLien', N'12345678', N'NV-009')
INSERT [dbo].[TaiKhoan] ([maTK], [UserName], [Password], [maNV]) VALUES (N'TK-010', N'VietHai', N'12345678', N'NV-012')
INSERT [dbo].[TaiKhoan] ([maTK], [UserName], [Password], [maNV]) VALUES (N'TK-011', N'VanSy', N'12345678', N'NV-014')
GO
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-001', N'Paracetamol', NULL, N'Paracetamol 500mg', N'DM-002', N'DVT-001', N'XX-004', 100, 500, 1000, CAST(N'2026-01-01T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-002', N'Panadol', NULL, N'Paracetamol', N'DM-002', N'DVT-002', N'XX-001', 200, 1000, 2000, CAST(N'2026-06-01T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-003', N'Aspirin', NULL, N'Aspirin 81mg', N'DM-007', N'DVT-001', N'XX-003', 150, 300, 800, CAST(N'2026-08-01T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-004', N'Amoxicillin', NULL, N'Amoxicillin 500mg', N'DM-003', N'DVT-001', N'XX-004', 120, 700, 1500, CAST(N'2026-12-01T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-005', N'Vitamin C', NULL, N'Acid Ascorbic', N'DM-004', N'DVT-002', N'XX-004', 250, 200, 500, CAST(N'2026-05-01T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-006', N'Omeprazole', NULL, N'Omeprazole 20mg', N'DM-005', N'DVT-001', N'XX-005', 80, 1500, 2500, CAST(N'2026-07-01T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-007', N'Telfast', NULL, N'Fexofenadine', N'DM-009', N'DVT-001', N'XX-001', 90, 1200, 2200, CAST(N'2026-09-01T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-008', N'Cold-Flu', NULL, N'Dextromethorphan', N'DM-001', N'DVT-001', N'XX-004', 100, 600, 1200, CAST(N'2026-10-01T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-009', N'Zinnat', NULL, N'Cefuroxime', N'DM-003', N'DVT-001', N'XX-003', 60, 4000, 6500, CAST(N'2026-12-01T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-010', N'Hapacol', NULL, N'Paracetamol + Vitamin C', N'DM-002', N'DVT-007', N'XX-004', 300, 500, 900, CAST(N'2026-03-01T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-011', N'Stugeron', NULL, N'Cinnarizine', N'DM-007', N'DVT-001', N'XX-004', 100, 700, 1300, CAST(N'2026-06-15T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-012', N'Clorpheniramin', NULL, N'Clorpheniramin 4mg', N'DM-009', N'DVT-001', N'XX-004', 120, 400, 800, CAST(N'2026-04-01T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-013', N'Augmentin', NULL, N'Amoxicillin + Clavulanic Acid', N'DM-003', N'DVT-002', N'XX-011', 50, 10000, 15000, CAST(N'2026-11-01T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-014', N'MeThylDopa', NULL, N'MeThylDopa 250mg', N'DM-006', N'DVT-001', N'XX-004', 60, 1800, 2700, CAST(N'2026-08-10T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-015', N'Napa Extra', NULL, N'Paracetamol + Cafein', N'DM-002', N'DVT-001', N'XX-004', 250, 700, 1200, CAST(N'2026-12-20T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-016', N'Magnesium B6', NULL, N'Magnesium + Vitamin B6', N'DM-004', N'DVT-001', N'XX-006', 140, 600, 1000, CAST(N'2026-05-25T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-017', N'HoAstex', NULL, N'Dextromethorphan', N'DM-010', N'DVT-003', N'XX-004', 100, 800, 1500, CAST(N'2026-07-20T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-018', N'Oresol', NULL, N'Natri + Kali', N'DM-004', N'DVT-005', N'XX-004', 300, 300, 500, CAST(N'2026-06-10T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-019', N'Kháng sinh Cephalexin', NULL, N'Cephalexin 500mg', N'DM-003', N'DVT-001', N'XX-004', 90, 1200, 2000, CAST(N'2026-08-18T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-020', N'Efferalgan', NULL, N'Paracetamol sủi bọt', N'DM-002', N'DVT-008', N'XX-004', 150, 1000, 1800, CAST(N'2026-12-31T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-021', N'Betadine', NULL, N'Iodine', N'DM-004', N'DVT-003', N'XX-004', 100, 2000, 3500, CAST(N'2026-11-15T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-022', N'Ibuprofen', NULL, N'Ibuprofen 200mg', N'DM-008', N'DVT-001', N'XX-003', 200, 900, 1500, CAST(N'2026-09-10T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-023', N'Glucose', NULL, N'Dextrose', N'DM-004', N'DVT-006', N'XX-004', 150, 500, 900, CAST(N'2026-07-30T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-024', N'Biseptol', NULL, N'Sulfamethoxazole + Trimethoprim', N'DM-003', N'DVT-001', N'XX-001', 90, 700, 1300, CAST(N'2026-12-15T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-025', N'Calcium Corbiere', NULL, N'Calcium + Vitamin D3', N'DM-004', N'DVT-006', N'XX-010', 60, 3000, 5000, CAST(N'2026-06-25T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-026', N'Tylenol', NULL, N'Paracetamol', N'DM-002', N'DVT-001', N'XX-001', 180, 600, 1100, CAST(N'2026-10-01T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-027', N'Agimoti', NULL, N'Domperidon', N'DM-005', N'DVT-001', N'XX-004', 110, 1000, 1800, CAST(N'2026-08-01T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-028', N'Mexcold', NULL, N'Chlorpheniramin + Dextromethorphan', N'DM-001', N'DVT-001', N'XX-004', 130, 800, 1500, CAST(N'2026-12-01T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-029', N'Ceruvin', NULL, N'Clopidogrel', N'DM-007', N'DVT-001', N'XX-007', 75, 2500, 4000, CAST(N'2026-12-20T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-030', N'De-cold', NULL, N'Paracetamol + Phenylephrine', N'DM-001', N'DVT-001', N'XX-004', 100, 700, 1300, CAST(N'2026-09-25T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-031', N'Maalox', NULL, N'Aluminum hydroxide + Magnesium hydroxide', N'DM-005', N'DVT-004', N'XX-004', 50, 2000, 3000, CAST(N'2026-07-01T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-032', N'Nifedipine', NULL, N'Nifedipine 10mg', N'DM-006', N'DVT-001', N'XX-004', 90, 1600, 2500, CAST(N'2026-06-01T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-033', N'Neurobion', NULL, N'Vitamin B1, B6, B12', N'DM-004', N'DVT-001', N'XX-011', 100, 2500, 4000, CAST(N'2026-10-15T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-034', N'Mofen', NULL, N'Ibuprofen 400mg', N'DM-008', N'DVT-001', N'XX-002', 120, 1000, 1600, CAST(N'2026-08-18T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-035', N'Rifampicin', NULL, N'Rifampicin 300mg', N'DM-003', N'DVT-001', N'XX-004', 80, 1400, 2200, CAST(N'2026-10-10T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-036', N'Chymotrypsin', NULL, N'Alpha chymotrypsin', N'DM-008', N'DVT-001', N'XX-004', 90, 1800, 2700, CAST(N'2026-12-25T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-037', N'Diclofenac', NULL, N'Diclofenac 50mg', N'DM-008', N'DVT-001', N'XX-004', 100, 1200, 2000, CAST(N'2026-11-05T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-038', N'Livolin', NULL, N'Phospholipid + Vitamin B', N'DM-004', N'DVT-002', N'XX-012', 110, 3500, 6000, CAST(N'2026-09-10T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-039', N'Zyrtec', NULL, N'Cetirizine', N'DM-009', N'DVT-001', N'XX-004', 130, 900, 1500, CAST(N'2026-06-15T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-040', N'Prospan', NULL, N'Chiết xuất thường xuân', N'DM-010', N'DVT-003', N'XX-004', 140, 3000, 5000, CAST(N'2026-07-05T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-041', N'1', NULL, N'1', N'DM-001', N'DVT-001', N'XX-001', 1, 1, 1, CAST(N'2025-04-18T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-042', N'1', NULL, N'1', N'DM-001', N'DVT-001', N'XX-001', 1, 1, 1, CAST(N'2025-04-17T00:00:00.000' AS DateTime))
INSERT [dbo].[Thuoc] ([maThuoc], [tenThuoc], [anh], [thanhPhanThuoc], [maDM], [maDVT], [maXX], [soLuong], [giaNhap], [donGia], [HSD]) VALUES (N'T-043', N'21', NULL, N'1', N'DM-001', N'DVT-001', N'XX-001', 1, 1, 1, CAST(N'2025-04-19T00:00:00.000' AS DateTime))
GO
INSERT [dbo].[XuatXu] ([maXX], [tenXX]) VALUES (N'XX-001', N'Mỹ')
INSERT [dbo].[XuatXu] ([maXX], [tenXX]) VALUES (N'XX-002', N'Nga')
INSERT [dbo].[XuatXu] ([maXX], [tenXX]) VALUES (N'XX-003', N'Đức')
INSERT [dbo].[XuatXu] ([maXX], [tenXX]) VALUES (N'XX-004', N'Việt Nam')
INSERT [dbo].[XuatXu] ([maXX], [tenXX]) VALUES (N'XX-005', N'Úc')
INSERT [dbo].[XuatXu] ([maXX], [tenXX]) VALUES (N'XX-006', N'CuBa')
INSERT [dbo].[XuatXu] ([maXX], [tenXX]) VALUES (N'XX-007', N'Hà Lan')
INSERT [dbo].[XuatXu] ([maXX], [tenXX]) VALUES (N'XX-008', N'Singapore')
INSERT [dbo].[XuatXu] ([maXX], [tenXX]) VALUES (N'XX-009', N'Thụy điển')
INSERT [dbo].[XuatXu] ([maXX], [tenXX]) VALUES (N'XX-010', N'Pháp')
INSERT [dbo].[XuatXu] ([maXX], [tenXX]) VALUES (N'XX-011', N'Nhật Bản')
INSERT [dbo].[XuatXu] ([maXX], [tenXX]) VALUES (N'XX-012', N'Hàn Quốc')
GO
ALTER TABLE [dbo].[CTHoaDon]  WITH CHECK ADD  CONSTRAINT [FK_CTHoaDon_HoaDon] FOREIGN KEY([maHD])
REFERENCES [dbo].[HoaDon] ([maHD])
GO
ALTER TABLE [dbo].[CTHoaDon] CHECK CONSTRAINT [FK_CTHoaDon_HoaDon]
GO
ALTER TABLE [dbo].[CTHoaDon]  WITH CHECK ADD  CONSTRAINT [FK_CTHoaDon_Thuoc] FOREIGN KEY([maThuoc])
REFERENCES [dbo].[Thuoc] ([maThuoc])
GO
ALTER TABLE [dbo].[CTHoaDon] CHECK CONSTRAINT [FK_CTHoaDon_Thuoc]
GO
ALTER TABLE [dbo].[CTPhieuDoi]  WITH CHECK ADD  CONSTRAINT [FK_CTPhieuDoi_CTPhieuDoi] FOREIGN KEY([maPD])
REFERENCES [dbo].[PhieuDoi] ([maPD])
GO
ALTER TABLE [dbo].[CTPhieuDoi] CHECK CONSTRAINT [FK_CTPhieuDoi_CTPhieuDoi]
GO
ALTER TABLE [dbo].[CTPhieuDoi]  WITH CHECK ADD  CONSTRAINT [FK_CTPhieuDoi_Thuoc] FOREIGN KEY([maThuocCu])
REFERENCES [dbo].[Thuoc] ([maThuoc])
GO
ALTER TABLE [dbo].[CTPhieuDoi] CHECK CONSTRAINT [FK_CTPhieuDoi_Thuoc]
GO
ALTER TABLE [dbo].[CTPhieuNhap]  WITH CHECK ADD  CONSTRAINT [FK_CTPhieuNhap_CTPhieuNhap] FOREIGN KEY([maThuoc])
REFERENCES [dbo].[Thuoc] ([maThuoc])
GO
ALTER TABLE [dbo].[CTPhieuNhap] CHECK CONSTRAINT [FK_CTPhieuNhap_CTPhieuNhap]
GO
ALTER TABLE [dbo].[CTPhieuNhap]  WITH CHECK ADD  CONSTRAINT [FK_CTPhieuNhap_PhieuNhap] FOREIGN KEY([maPN])
REFERENCES [dbo].[PhieuNhap] ([maPN])
GO
ALTER TABLE [dbo].[CTPhieuNhap] CHECK CONSTRAINT [FK_CTPhieuNhap_PhieuNhap]
GO
ALTER TABLE [dbo].[CTPhieuTra]  WITH CHECK ADD  CONSTRAINT [FK_CTPhieuTra_PhieuTra] FOREIGN KEY([maPT])
REFERENCES [dbo].[PhieuTra] ([maPT])
GO
ALTER TABLE [dbo].[CTPhieuTra] CHECK CONSTRAINT [FK_CTPhieuTra_PhieuTra]
GO
ALTER TABLE [dbo].[CTPhieuTra]  WITH CHECK ADD  CONSTRAINT [FK_CTPhieuTra_Thuoc] FOREIGN KEY([maThuoc])
REFERENCES [dbo].[Thuoc] ([maThuoc])
GO
ALTER TABLE [dbo].[CTPhieuTra] CHECK CONSTRAINT [FK_CTPhieuTra_Thuoc]
GO
ALTER TABLE [dbo].[HoaDon]  WITH CHECK ADD  CONSTRAINT [FK_HoaDon_KhachHang] FOREIGN KEY([maKH])
REFERENCES [dbo].[KhachHang] ([maKH])
GO
ALTER TABLE [dbo].[HoaDon] CHECK CONSTRAINT [FK_HoaDon_KhachHang]
GO
ALTER TABLE [dbo].[HoaDon]  WITH CHECK ADD  CONSTRAINT [FK_HoaDon_NhanVien] FOREIGN KEY([maNV])
REFERENCES [dbo].[NhanVien] ([maNV])
GO
ALTER TABLE [dbo].[HoaDon] CHECK CONSTRAINT [FK_HoaDon_NhanVien]
GO
ALTER TABLE [dbo].[PhieuDoi]  WITH CHECK ADD  CONSTRAINT [FK_PhieuDoi_HoaDon] FOREIGN KEY([maHD])
REFERENCES [dbo].[HoaDon] ([maHD])
GO
ALTER TABLE [dbo].[PhieuDoi] CHECK CONSTRAINT [FK_PhieuDoi_HoaDon]
GO
ALTER TABLE [dbo].[PhieuDoi]  WITH CHECK ADD  CONSTRAINT [FK_PhieuDoi_KhachHang] FOREIGN KEY([maKH])
REFERENCES [dbo].[KhachHang] ([maKH])
GO
ALTER TABLE [dbo].[PhieuDoi] CHECK CONSTRAINT [FK_PhieuDoi_KhachHang]
GO
ALTER TABLE [dbo].[PhieuDoi]  WITH CHECK ADD  CONSTRAINT [FK_PhieuDoi_NhanVien] FOREIGN KEY([maNV])
REFERENCES [dbo].[NhanVien] ([maNV])
GO
ALTER TABLE [dbo].[PhieuDoi] CHECK CONSTRAINT [FK_PhieuDoi_NhanVien]
GO
ALTER TABLE [dbo].[PhieuNhap]  WITH CHECK ADD  CONSTRAINT [FK_PhieuNhap_NhaCungCap] FOREIGN KEY([maNCC])
REFERENCES [dbo].[NhaCungCap] ([maNCC])
GO
ALTER TABLE [dbo].[PhieuNhap] CHECK CONSTRAINT [FK_PhieuNhap_NhaCungCap]
GO
ALTER TABLE [dbo].[PhieuNhap]  WITH CHECK ADD  CONSTRAINT [FK_PhieuNhap_NhanVien] FOREIGN KEY([maNV])
REFERENCES [dbo].[NhanVien] ([maNV])
GO
ALTER TABLE [dbo].[PhieuNhap] CHECK CONSTRAINT [FK_PhieuNhap_NhanVien]
GO
ALTER TABLE [dbo].[PhieuTra]  WITH CHECK ADD  CONSTRAINT [FK_PhieuTra_KhachHang] FOREIGN KEY([maKH])
REFERENCES [dbo].[KhachHang] ([maKH])
GO
ALTER TABLE [dbo].[PhieuTra] CHECK CONSTRAINT [FK_PhieuTra_KhachHang]
GO
ALTER TABLE [dbo].[PhieuTra]  WITH CHECK ADD  CONSTRAINT [FK_PhieuTra_NhanVien] FOREIGN KEY([maNV])
REFERENCES [dbo].[NhanVien] ([maNV])
GO
ALTER TABLE [dbo].[PhieuTra] CHECK CONSTRAINT [FK_PhieuTra_NhanVien]
GO
ALTER TABLE [dbo].[TaiKhoan]  WITH CHECK ADD  CONSTRAINT [FK__TaiKhoan__maNV__5535A963] FOREIGN KEY([maNV])
REFERENCES [dbo].[NhanVien] ([maNV])
GO
ALTER TABLE [dbo].[TaiKhoan] CHECK CONSTRAINT [FK__TaiKhoan__maNV__5535A963]
GO
ALTER TABLE [dbo].[Thuoc]  WITH CHECK ADD  CONSTRAINT [FK_Thuoc_DanhMuc] FOREIGN KEY([maDM])
REFERENCES [dbo].[DanhMuc] ([maDM])
GO
ALTER TABLE [dbo].[Thuoc] CHECK CONSTRAINT [FK_Thuoc_DanhMuc]
GO
ALTER TABLE [dbo].[Thuoc]  WITH CHECK ADD  CONSTRAINT [FK_Thuoc_DonViTinh] FOREIGN KEY([maDVT])
REFERENCES [dbo].[DonViTinh] ([maDVT])
GO
ALTER TABLE [dbo].[Thuoc] CHECK CONSTRAINT [FK_Thuoc_DonViTinh]
GO
ALTER TABLE [dbo].[Thuoc]  WITH CHECK ADD  CONSTRAINT [FK_Thuoc_XuatXu] FOREIGN KEY([maXX])
REFERENCES [dbo].[XuatXu] ([maXX])
GO
ALTER TABLE [dbo].[Thuoc] CHECK CONSTRAINT [FK_Thuoc_XuatXu]
GO
USE [master]
GO
ALTER DATABASE [QLNThuoc] SET  READ_WRITE 
GO
